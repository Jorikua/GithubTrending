package com.example.githubtrending.di

import com.example.base.coroutines.BackgroundDispatcher.Background
import com.example.base.resource.ResourceManager
import com.example.base.resource.ResourceManagerImpl
import com.example.base.coroutines.CoroutinesDispatcherProvider
import com.example.base.network.ErrorHandler
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single<ResourceManager> { ResourceManagerImpl(androidContext()) }
    single {
        CoroutinesDispatcherProvider(
            main = Dispatchers.Main,
            computation = Dispatchers.Background,
            io = Dispatchers.Background
        )
    }
    single { ErrorHandler(get()) }
}