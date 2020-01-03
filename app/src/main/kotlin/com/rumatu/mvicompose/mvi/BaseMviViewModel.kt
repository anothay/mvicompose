package com.rumatu.mvicompose.mvi

import androidx.lifecycle.ViewModel
import com.rumatu.mvicompose.mvi.contracts.MviAction
import com.rumatu.mvicompose.mvi.contracts.MviIntent
import com.rumatu.mvicompose.mvi.contracts.MviResult
import com.rumatu.mvicompose.mvi.contracts.MviViewModel
import com.rumatu.mvicompose.mvi.contracts.MviViewState
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

open class BaseMviViewModel<I : MviIntent<A>, A : MviAction, R : MviResult, S : MviViewState>(
    private val processor: MviActionProcessor<A, R>,
    private val intialIntent: Class<out I>,
    initialState: S,
    reducer: Reducer<S, R>
) : MviViewModel<I, A, S>, ViewModel() {

    // Filter out initial intent if emitted more than once - this helps bypass device rotation
    // issues where intent is emitted twice
    private val intentFilter: ObservableTransformer<I, I>
        get() = ObservableTransformer { intents ->
            intents.publish { shared ->
                Observable.merge(
                    shared.ofType(intialIntent).take(1),
                    shared.filter { !intialIntent.isInstance(intialIntent) }
                )
            }
        }

    // Proxy subject that keeps the stream alive during config changes
    private val intentSink: PublishSubject<I> = PublishSubject.create()

    final override val states: Observable<S> = intentSink
        .compose(intentFilter)
        .map { it.mapToAction() }
        .compose { processor.apply(it) }
        .scan(initialState, reducer)
        .distinctUntilChanged()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        // Replay last event on reconnection, ie allow a View to get the last state
        // on rebinding after a config change.
        .replay(1)
        // Create the stream immediately without waiting for subscribers. Stream stays
        // alive even when the UI disconnects.
        .autoConnect(0)

    final override fun processIntents(intents: Observable<I>) = intents.subscribe(intentSink)

    // Internal state handling
    private val internalDisposable = states.subscribe(::internalLogger, ::crashHandler)

    // Log all ViewStates
    private fun internalLogger(state: S) = Timber.i("$state")

    // Raise any unhandled exceptions
    private fun crashHandler(throwable: Throwable): Unit = throw throwable
}
