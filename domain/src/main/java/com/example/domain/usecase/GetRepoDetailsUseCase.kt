package com.example.domain.usecase

import com.example.base.CoroutinesDispatcherProvider
import com.example.base.domain.UseCase
import com.example.base.network.ErrorHandler
import com.example.data.Repository
import com.example.domain.mapper.toRepoDetails
import com.example.domain.model.RepoDetails
import kotlinx.coroutines.withContext

class GetRepoDetailsUseCase(
    private val repository: Repository,
    private val dispatcherProvider: CoroutinesDispatcherProvider,
    errorHandler: ErrorHandler
) : UseCase<GetRepoDetailsUseCase.Params, RepoDetails>(errorHandler) {

    class Params(val author: String, val repoName: String)

    override suspend fun run(params: Params): RepoDetails {
        return withContext(dispatcherProvider.io) {
            repository.getRepoDetails(params.author, params.repoName).toRepoDetails()
        }
    }
}