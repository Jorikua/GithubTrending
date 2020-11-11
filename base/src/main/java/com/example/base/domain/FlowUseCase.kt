package com.example.base.domain

import com.example.base.data.DataResult
import com.example.base.network.ErrorHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

abstract class FlowUseCase<in Params, out Type : Any>(private val errorHandler: ErrorHandler) {

    protected abstract suspend fun run(params: Params): Flow<Type>

    suspend operator fun invoke(params: Params): Flow<DataResult<Type>> {
        return run(params)
            .map { DataResult.Success(it) as DataResult<Type> }
            .catch { emit(errorHandler.handleError(it)) }
    }
}