package com.rumatu.mvicompose.ui.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding3.swiperefreshlayout.refreshes
import com.rumatu.mvicompose.R
import com.rumatu.mvicompose.mvi.contracts.MviView
import com.rumatu.mvicompose.ui.detail.DetailActivity
import com.rumatu.mvicompose.ui.list.actions.PostsAction
import com.rumatu.mvicompose.ui.list.adapter.PostsListAdapter
import com.rumatu.mvicompose.ui.list.displaymodels.ListDisplayModel
import com.rumatu.mvicompose.ui.list.intents.UserIntent
import com.rumatu.mvicompose.ui.list.models.ListModel
import com.rumatu.mvicompose.ui.list.viewstate.PostsViewState
import com.rumatu.mvicompose.utils.toast
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.posts_list.recyclerView
import kotlinx.android.synthetic.main.posts_list.swipeRefreshLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListActivity :
    AppCompatActivity(R.layout.posts_list),
    MviView<UserIntent, PostsAction, PostsViewState> {

    /**
     * If there were more user actions, here we'd merge the Observables into a single stream of
     * UserIntent objects.
     */
    override val intents: Observable<UserIntent> by lazy {
        swipeRefreshLayout.refreshes()
            .map { UserIntent.Refresh }
            .cast(UserIntent::class.java)
            // Emit initial intent on first subscribe to trigger initial loading
            .startWith(UserIntent.InitialIntent)
            .observeOn(AndroidSchedulers.mainThread(), false)
    }

    private val compositeDisposable = CompositeDisposable()
    private val model: ListModel by viewModel()
    private val adapter by lazy { PostsListAdapter { onPostSelected(it) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.posts_list)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()

        compositeDisposable +=
            model.states
                .subscribeBy(onNext = this::render)

        model.processIntents(intents)
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.clear()
    }

    override fun render(state: PostsViewState) {
        with(state) {
            swipeRefreshLayout.isRefreshing = refreshing
            adapter.items = data
            error?.consume { toast(it ?: "Error fetching posts") }
        }
    }

    private fun onPostSelected(displayModel: ListDisplayModel) {
        DetailActivity.start(this, displayModel)
    }
}
