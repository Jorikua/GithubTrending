package com.example.githubtrending.di

import com.example.githubtrending.ui.MainViewModel
import com.example.githubtrending.ui.details.RepoDetailsViewModel
import com.example.githubtrending.ui.filter.granularity.GranularityViewModel
import com.example.githubtrending.ui.filter.programming_language.ProgrammingViewModel
import com.example.githubtrending.ui.filter.spoken_language.SpokenViewModel
import com.example.githubtrending.ui.trending.TrendingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { TrendingViewModel(get(), get()) }
    viewModel { MainViewModel() }
    viewModel { SpokenViewModel(get(), get(), get()) }
    viewModel { ProgrammingViewModel(get(), get(), get()) }
    viewModel { GranularityViewModel(get()) }
    viewModel { RepoDetailsViewModel(get()) }
}