package com.example.test

import com.example.base.data.DataResult
import com.example.base.network.DataException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

val <T : Any> T.toSuccessResult: DataResult<T> get() = DataResult.Success(this)

const val DEFAULT_ERROR_MESSAGE = "Something went wrong"
val <T : Any> T.toFailureResult: DataResult<T>
    get() = DataResult.Failure(
        DataException.UnknownException(
            DEFAULT_ERROR_MESSAGE
        )
    )

fun <T> T.asFlow(): Flow<T> {
    return flow { emit(this@asFlow) }
}