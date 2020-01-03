package com.rumatu.mvicompose.ui.list.processors

import com.rumatu.api.service.ApiService
import com.rumatu.api.service.models.User
import com.rumatu.mvicompose.mvi.MviActionProcessor
import com.rumatu.mvicompose.ui.list.actions.PostsAction
import com.rumatu.mvicompose.ui.list.displaymodels.ListDisplayModel
import com.rumatu.mvicompose.ui.list.results.PostsResult
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.rxkotlin.Singles
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 * In a more complex app, I'd instead pass in a Repository here which handles fetching, caching and
 * storing data offline using Room, with different fetching strategies for local-first and force
 * refresh. You generally wouldn't pass a network service in here directly.
 */
class PostsListProcessor(api: ApiService) :
    MviActionProcessor<PostsAction, PostsResult>() {

    override fun getActionProcessors(
        shared: Observable<PostsAction>
    ): List<Observable<PostsResult>> = listOf(shared.connect(loadPostsProcessor))

    private val loadPostsProcessor: ObservableTransformer<PostsAction, PostsResult> =
        ObservableTransformer { actions ->
            actions.switchMap {
                Singles.zip(
                    api.getComments(),
                    api.getPosts(),
                    api.getUsers()
                ).subscribeOn(Schedulers.io())
                    .toObservable()
                    .map { (comments, posts, users) ->
                        val userMap: Map<Int, User> = users.associateBy { it.id }
                        val commentCountMap = comments
                            .groupingBy { it.postId }
                            .eachCount()
                            .toMap()

                        return@map PostsResult.Success(
                            posts.map {
                                ListDisplayModel(
                                    it.title,
                                    it.body,
                                    commentCountMap[it.id] ?: -1,
                                    userMap[it.userId]?.userName ?: "Unknown"
                                )
                            }
                        )
                    }
                    .cast(PostsResult::class.java)
                    .doOnError { Timber.e(it) }
                    .onErrorReturn { PostsResult.Error(it.message ?: "Error loading posts") }
                    .startWith(PostsResult.Loading)
            }
        }
}
