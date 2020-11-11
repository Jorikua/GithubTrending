package com.example.githubtrending.di

import com.example.data.Repository
import com.example.data.source.LocalDataSource
import org.koin.dsl.module

val repoModule = module {
    single { Repository(get(), get(), get()) }
    single { LocalDataSource(get(), get()) }
}