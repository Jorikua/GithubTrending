package com.example.test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.base.CoroutinesDispatcherProvider
import kotlinx.coroutines.Dispatchers
import org.junit.Rule


open class BaseTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesMainDispatcherRule = CoroutinesMainDispatcherRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    val dispatcherProvider: CoroutinesDispatcherProvider =
        CoroutinesDispatcherProvider(Dispatchers.Main, Dispatchers.Main, Dispatchers.Main)
}