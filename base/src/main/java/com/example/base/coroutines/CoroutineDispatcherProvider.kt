package com.example.base.coroutines

import kotlinx.coroutines.CoroutineDispatcher

data class CoroutinesDispatcherProvider(
    val main: CoroutineDispatcher,
    val computation: CoroutineDispatcher,
    val io: CoroutineDispatcher
)