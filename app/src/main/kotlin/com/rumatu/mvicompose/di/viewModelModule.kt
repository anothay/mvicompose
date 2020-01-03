package com.rumatu.mvicompose.di

import com.rumatu.mvicompose.ui.list.models.ListModel
import com.rumatu.mvicompose.ui.list.processors.PostsListProcessor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { ListModel(PostsListProcessor(get())) }
}
