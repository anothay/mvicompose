package com.rumatu.mvicompose.ui.list.actions

import com.rumatu.mvicompose.mvi.contracts.MviAction

sealed class PostsAction : MviAction {

    object LoadPosts : PostsAction()
}
