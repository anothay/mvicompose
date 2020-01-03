package com.rumatu.mvicompose.ui.list.results

import com.rumatu.mvicompose.mvi.contracts.MviResult
import com.rumatu.mvicompose.ui.list.displaymodels.ListDisplayModel

sealed class PostsResult : MviResult {

    object Loading : PostsResult()
    data class Error(val errorMessage: String) : PostsResult()
    data class Success(val data: List<ListDisplayModel>) : PostsResult()
}
