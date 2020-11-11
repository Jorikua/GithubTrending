package com.example.githubtrending.di

import android.content.Context
import com.example.data.GithubApiInterface
import com.example.data.TrendingApiInterface
import com.example.githubtrending.BuildConfig
import com.squareup.moshi.Moshi
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

val networkModule = module {
    single { Moshi.Builder().build() }
    single(named(Github.TRENDING)) { provideTrendingRetrofit() }
    single(named(Github.BASIC)) { provideGithubRetrofit() }
    single { provideTrendingApi(get(named(Github.TRENDING))) }
    single { provideGithubApi(get(named(Github.BASIC))) }
    single { provideHttpClient(get()) }
}

private fun provideTrendingApi(retrofit: Retrofit): TrendingApiInterface {
    return retrofit.create()
}

private fun provideGithubApi(retrofit: Retrofit): GithubApiInterface {
    return retrofit.create()
}

private fun provideHttpClient(context: Context): OkHttpClient {
    val size: Long = 10 * 1024 * 1024 // 10 MB
    val cache = Cache(context.cacheDir, size)
    return OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) BODY else NONE
        })
        .cache(cache)
        .build()
}

private fun Scope.provideTrendingRetrofit(): Retrofit {
    return Retrofit.Builder()
        .client(get())
        .baseUrl(BuildConfig.TRENDING_GITHUB_URL)
        .addConverterFactory(MoshiConverterFactory.create(get()))
        .build()
}

private fun Scope.provideGithubRetrofit(): Retrofit {
    return Retrofit.Builder()
        .client(get())
        .baseUrl(BuildConfig.GITHUB_URL)
        .addConverterFactory(MoshiConverterFactory.create(get()))
        .build()
}

enum class Github {
    TRENDING, BASIC
}