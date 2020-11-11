package com.example.base.domain

import com.example.base.data.DataResult
import com.example.base.network.ErrorHandler

abstract class UseCase<in Params, out Type : Any>(private val errorHandler: ErrorHandler) {

    protected abstract suspend fun run(params: Params): Type

    suspend operator fun invoke(params: Params): DataResult<Type> {
        return try {
                val result = run(params)
                DataResult.Success(result)
            } catch (e: Exception) {
                //parse exception
                errorHandler.handleError(e)
            }
        }
}