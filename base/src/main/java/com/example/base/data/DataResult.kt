package com.example.base.data

import com.example.base.network.DataException

sealed class DataResult<out T : Any?> {

    data class Success<out T : Any>(val data: T) : DataResult<T>()
    data class Failure(val exception: DataException) : DataResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Failure -> "Failure[exception=$exception]"
        }
    }
}