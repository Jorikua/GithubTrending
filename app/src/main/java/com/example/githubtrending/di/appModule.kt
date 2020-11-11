package com.example.githubtrending.di

import com.example.base.resource.ResourceManager
import com.example.base.resource.ResourceManagerImpl
import com.example.base.CoroutinesDispatcherProvider
import com.example.base.network.ErrorHandler
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single<ResourceManager> { ResourceManagerImpl(androidContext()) }
    single {
        CoroutinesDispatcherProvider(
            Dispatchers.Main,
            Dispatchers.Default,
            Dispatchers.IO
        )
    }
    single { ErrorHandler(get()) }
}