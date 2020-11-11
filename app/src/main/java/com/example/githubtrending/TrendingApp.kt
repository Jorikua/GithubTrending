package com.example.githubtrending

import android.app.Application
import com.airbnb.epoxy.EpoxyAsyncUtil
import com.airbnb.epoxy.EpoxyController
import com.example.githubtrending.di.appModule
import com.example.githubtrending.di.controllerModule
import com.example.githubtrending.di.networkModule
import com.example.githubtrending.di.repoModule
import com.example.githubtrending.di.useCaseModule
import com.example.githubtrending.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class TrendingApp: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
        initEpoxy()

    }

    private fun initKoin() {
        startKoin {
            androidContext(this@TrendingApp)
            loadKoinModules(
                listOf(
                    appModule,
                    controllerModule,
                    networkModule,
                    repoModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }

    private fun initEpoxy() {
        with(EpoxyAsyncUtil.getAsyncBackgroundHandler()) {
            EpoxyController.defaultDiffingHandler = this
            EpoxyController.defaultModelBuildingHandler = this
        }
        EpoxyController.setGlobalDuplicateFilteringDefault(true)
    }
}