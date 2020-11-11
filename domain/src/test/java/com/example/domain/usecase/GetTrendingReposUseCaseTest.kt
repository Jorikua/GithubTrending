package com.example.domain.usecase

import com.example.base.data.DataResult
import com.example.base.network.DataException
import com.example.base.network.ErrorHandler
import com.example.data.Repository
import com.example.data.model.RepoResponse
import com.example.domain.mapper.toTrendingRepo
import com.example.domain.model.Granularity
import com.example.test.BaseTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class GetTrendingReposUseCaseTest : BaseTest() {

    private lateinit var getTrendingReposUseCase: GetTrendingReposUseCase

    private val repository: Repository = mock()
    private val errorHandler: ErrorHandler = mock()

    @Before
    fun setUp() {
        getTrendingReposUseCase =
            GetTrendingReposUseCase(repository, dispatcherProvider, errorHandler)
    }

    @Test
    fun `get programming languages success`() {
        testCoroutineRule.runBlockingTest {

            val list = listOf(getRepoResponse())
            val params = GetTrendingReposUseCase.Params(
                Granularity.DAILY,
                null,
                null
            )

            whenever(
                repository.getTrendingRepos(
                    params.granularity.apiName,
                    null,
                    null
                )
            ).thenReturn(list)

            val result = getTrendingReposUseCase.invoke(params)

            Assert.assertFalse(result is DataResult.Failure)

            val data = (result as DataResult.Success).data
            Assert.assertNotNull(data)
            Assert.assertEquals(list.size, data.size)
            Assert.assertEquals(list[0].toTrendingRepo(), data[0])
        }
    }

    @Test
    fun `get programming languages failure`() {
        testCoroutineRule.runBlockingTest {

            val exception = NullPointerException()
            val message = "Error"
            val params = GetTrendingReposUseCase.Params(Granularity.DAILY, null, null)

            whenever(errorHandler.handleError(exception)).thenReturn(
                DataResult.Failure(
                    DataException.UnknownException(message)
                )
            )
            whenever(repository.getTrendingRepos(params.granularity.apiName, null, null)).thenThrow(
                exception
            )

            val result = getTrendingReposUseCase.invoke(params)

            Assert.assertTrue(result is DataResult.Failure)
            val failure = result as DataResult.Failure
            Assert.assertEquals(message, failure.exception.message)
        }
    }

    private fun getRepoResponse(): RepoResponse {
        return RepoResponse(
            author = "a",
            name = "b",
            avatar = null,
            url = "as",
            description = "desc",
            language = "language",
            languageColor = "color",
            stars = 1,
            forks = 1,
            currentPeriodStars = 1
        )
    }
}