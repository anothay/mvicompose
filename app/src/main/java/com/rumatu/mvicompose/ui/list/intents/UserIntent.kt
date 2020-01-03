package com.rumatu.mvicompose.ui.list.intents

import com.rumatu.mvicompose.mvi.contracts.MviIntent
import com.rumatu.mvicompose.ui.list.actions.PostsAction

sealed class UserIntent : MviIntent<PostsAction> {

    object InitialIntent : UserIntent()

    object Refresh : UserIntent()

    override fun mapToAction(): PostsAction = when (this) {
        InitialIntent -> PostsAction.LoadPosts
        Refresh -> PostsAction.LoadPosts
    }
}
