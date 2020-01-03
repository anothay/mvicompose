package com.rumatu.mvicompose.mvi.contracts
import com.rumatu.mvicompose.mvi.MviActionProcessor

/**
 * Actions define logic that executes by [MviActionProcessor].
 * [MviActionProcessor] executes Actions inside a [MviViewModel], this is the only place where
 * side effects should happen (IO operations).
 */
interface MviAction
