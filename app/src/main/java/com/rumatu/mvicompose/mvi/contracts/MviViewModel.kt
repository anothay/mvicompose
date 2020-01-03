package com.rumatu.mvicompose.mvi.contracts

import io.reactivex.Observable

interface MviViewModel<I : MviIntent<A>, A : MviAction, S : MviViewState> {

    val states: Observable<S>

    fun processIntents(intents: Observable<I>)
}
