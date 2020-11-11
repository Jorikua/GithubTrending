package com.example.base.network

import com.example.base.R
import com.example.base.data.DataResult
import com.example.base.resource.ResourceManager
import retrofit2.HttpException

class ErrorHandler(
    private val resources: ResourceManager,
) {

    fun handleError(e: Throwable): DataResult.Failure {
        return when(e) {
            is HttpException -> DataResult.Failure(DataException.NoNetwork(resources.getString(R.string.please_check_connection)))
            else -> DataResult.Failure(DataException.UnknownException(resources.getString(R.string.something_went_wrong_please_try_again)))
        }
    }
}