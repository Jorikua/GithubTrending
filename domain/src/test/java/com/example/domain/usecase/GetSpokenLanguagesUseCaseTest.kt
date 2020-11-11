package com.example.domain.usecase

import com.example.base.data.DataResult
import com.example.base.network.DataException
import com.example.base.network.ErrorHandler
import com.example.data.Repository
import com.example.data.model.LanguageResponse
import com.example.domain.mapper.toLanguage
import com.example.test.BaseTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class GetSpokenLanguagesUseCaseTest : BaseTest() {

    private lateinit var getSpokenLanguagesUseCase: GetSpokenLanguagesUseCase

    private val repository: Repository = mock()
    private val errorHandler: ErrorHandler = mock()

    @Before
    fun setUp() {
        getSpokenLanguagesUseCase =
            GetSpokenLanguagesUseCase(repository, dispatcherProvider, errorHandler)
    }

    @Test
    fun `get programming languages success`() {
        testCoroutineRule.runBlockingTest {

            val list = listOf(LanguageResponse("a", "b"))

            whenever(repository.getSpokenLanguages()).thenReturn(list)

            val result = getSpokenLanguagesUseCase.invoke(Unit)

            Assert.assertFalse(result is DataResult.Failure)

            val data = (result as DataResult.Success).data
            Assert.assertNotNull(data)
            Assert.assertEquals(list.size, data.size)
            Assert.assertEquals(list[0].toLanguage(), data[0])
        }
    }

    @Test
    fun `get programming languages failure`() {
        testCoroutineRule.runBlockingTest {

            val exception = NullPointerException()
            val message = "Error"

            whenever(errorHandler.handleError(exception)).thenReturn(
                DataResult.Failure(
                    DataException.UnknownException(message)
                )
            )
            whenever(repository.getSpokenLanguages()).thenThrow(exception)

            val result = getSpokenLanguagesUseCase.invoke(Unit)

            Assert.assertTrue(result is DataResult.Failure)
            val failure = result as DataResult.Failure
            Assert.assertEquals(message, failure.exception.message)
        }
    }
}