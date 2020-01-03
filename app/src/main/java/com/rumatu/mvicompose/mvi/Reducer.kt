package com.rumatu.mvicompose.mvi
import com.rumatu.mvicompose.mvi.contracts.MviResult
import com.rumatu.mvicompose.mvi.contracts.MviViewState

/**
 * The Reducer is responsible for generating the [MviViewState] which the View will use to render
 * itself. [Reducer] takes the latest [MviViewState] available, apply the latest [MviResult] to it
 * and return a whole new [MviViewState].
 */
typealias Reducer<S, R> = (state: S, result: R) -> S
