package com.example.githubtrending.di

import com.example.githubtrending.ui.trending.TrendingController
import org.koin.dsl.module

val controllerModule = module {
    factory { (callback: TrendingController.Callback) -> TrendingController(callback) }
}