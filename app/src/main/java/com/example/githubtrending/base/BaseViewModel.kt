package com.example.githubtrending.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.base.data.DataResult
import com.example.base.network.DataException
import com.hadilq.liveevent.LiveEvent

abstract class BaseViewModel<State: BaseState>(defaultState: State) : ViewModel() {
    var exception = LiveEvent<DataException>()

    val isExecutingUseCase = LiveEvent<Boolean>()

    val state = MutableLiveData(defaultState)

    protected val currentState: State
        get() = state.value ?: throw NullPointerException()

    protected inline fun <T> DataResult<T>.handleSuccess(crossinline block: (T) -> Unit) {
        when (this) {
            is DataResult.Failure -> handleFailure(exception)
            is DataResult.Success -> block.invoke(data)
        }

        isLoading(false)
    }

    protected suspend inline fun <T> DataResult<T>.handleSuccessSuspend(crossinline block: suspend (T) -> Unit) {
        when (this) {
            is DataResult.Failure -> handleFailure(exception)
            is DataResult.Success -> block.invoke(data)
        }

        isLoading(false)
    }

    fun isLoading(isLoading: Boolean) {
        isExecutingUseCase.value = isLoading
    }

    fun handleFailure(failure: DataException) {
        exception.value = failure
    }
}