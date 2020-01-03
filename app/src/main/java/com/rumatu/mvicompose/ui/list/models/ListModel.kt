package com.rumatu.mvicompose.ui.list.models

import com.rumatu.mvicompose.mvi.BaseMviViewModel
import com.rumatu.mvicompose.ui.list.actions.PostsAction
import com.rumatu.mvicompose.ui.list.intents.UserIntent
import com.rumatu.mvicompose.ui.list.processors.PostsListProcessor
import com.rumatu.mvicompose.ui.list.results.PostsResult
import com.rumatu.mvicompose.ui.list.viewstate.PostsViewState

class ListModel(
    processor: PostsListProcessor
) : BaseMviViewModel<UserIntent, PostsAction, PostsResult, PostsViewState>(
    processor,
    UserIntent.InitialIntent::class.java,
    PostsViewState(),
    PostsViewState.reducer
)
