package com.example.domain.usecase

import com.example.base.coroutines.CoroutinesDispatcherProvider
import com.example.base.domain.UseCase
import com.example.base.network.ErrorHandler
import com.example.data.Repository
import com.example.domain.mapper.toLanguage
import com.example.domain.model.Language
import kotlinx.coroutines.withContext

class GetSpokenLanguagesUseCase(
    private val repository: Repository,
    private val dispatcherProvider: CoroutinesDispatcherProvider,
    errorHandler: ErrorHandler
) : UseCase<Unit, List<Language>>(errorHandler) {
    override suspend fun run(params: Unit): List<Language> {
        return withContext(dispatcherProvider.io) {
            repository.getSpokenLanguages().map { it.toLanguage() }
        }
    }
}