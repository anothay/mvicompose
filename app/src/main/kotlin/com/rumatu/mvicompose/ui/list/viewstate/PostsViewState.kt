package com.rumatu.mvicompose.ui.list.viewstate

import com.rumatu.mvicompose.mvi.Reducer
import com.rumatu.mvicompose.mvi.ViewStateEvent
import com.rumatu.mvicompose.mvi.contracts.MviViewState
import com.rumatu.mvicompose.ui.list.displaymodels.ListDisplayModel
import com.rumatu.mvicompose.ui.list.results.PostsResult

data class PostsViewState(
    val refreshing: Boolean = false,
    val data: List<ListDisplayModel> = emptyList(),
    val error: ViewStateEvent<String>? = null
) : MviViewState {

    companion object {

        val reducer: Reducer<PostsViewState, PostsResult> = { state, result ->
            when (result) {
                PostsResult.Loading -> state.copy(refreshing = true, error = null)
                is PostsResult.Error -> state.copy(
                    refreshing = false,
                    error = ViewStateEvent(result.errorMessage)
                )
                is PostsResult.Success -> state.copy(
                    refreshing = false,
                    error = null,
                    data = result.data
                )
            }
        }
    }
}
