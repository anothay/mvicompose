package com.rumatu.mvicompose.mvi.contracts

import io.reactivex.Observable

interface MviView<I : MviIntent<A>, A : MviAction, S : MviViewState> {

    val intents: Observable<I>

    fun render(state: S)
}
