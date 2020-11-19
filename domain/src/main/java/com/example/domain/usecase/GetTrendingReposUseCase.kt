package com.example.domain.usecase

import com.example.base.coroutines.CoroutinesDispatcherProvider
import com.example.base.domain.UseCase
import com.example.base.network.ErrorHandler
import com.example.data.Repository
import com.example.domain.mapper.toTrendingRepo
import com.example.domain.model.Granularity
import com.example.domain.model.TrendingRepo
import kotlinx.coroutines.withContext

class GetTrendingReposUseCase(
    private val repository: Repository,
    private val dispatcherProvider: CoroutinesDispatcherProvider,
    errorHandler: ErrorHandler
) : UseCase<GetTrendingReposUseCase.Params, List<TrendingRepo>>(errorHandler) {
    data class Params(
        val granularity: Granularity,
        val spokenLanguageCode: String?,
        val programmingLanguageCode: String?
    )

    override suspend fun run(params: Params): List<TrendingRepo> {
        return withContext(dispatcherProvider.io) {
            repository.getTrendingRepos(
                granularity = params.granularity.apiName,
                spokenLanguage = params.spokenLanguageCode,
                programmingLanguage = params.programmingLanguageCode
            ).map { it.toTrendingRepo() }
        }
    }
}