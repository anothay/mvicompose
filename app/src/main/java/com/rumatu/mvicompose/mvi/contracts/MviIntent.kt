package com.rumatu.mvicompose.mvi.contracts

/**
 * Intents represent intents from the user - whether opening the screen, clicking a button,
 * entering text, reaching the bottom of a list etc.
 *
 * Intents are translated into their respective logical [MviAction]. For instance, "opening a page"
 * intent might translate to "invalidate the cache and load fresh data". The intent and action
 * are often similar but this is important to avoid the data flow being too tightly coupled to
 * the UI.
 */
interface MviIntent<A : MviAction> {

    /**
     * Maps an [MviIntent] to it's associated [MviAction].
     */
    fun mapToAction(): A
}
