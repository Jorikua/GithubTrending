package com.example.githubtrending.di

import com.example.domain.usecase.GetProgrammingLanguagesUseCase
import com.example.domain.usecase.GetRepoDetailsUseCase
import com.example.domain.usecase.GetSpokenLanguagesUseCase
import com.example.domain.usecase.GetTrendingReposUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetTrendingReposUseCase(get(), get(), get()) }
    factory { GetSpokenLanguagesUseCase(get(), get(), get()) }
    factory { GetProgrammingLanguagesUseCase(get(), get(), get()) }
    factory { GetRepoDetailsUseCase(get(), get(), get()) }
}