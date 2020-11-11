package com.example.domain.usecase

import com.example.base.data.DataResult
import com.example.base.network.DataException
import com.example.base.network.ErrorHandler
import com.example.data.Repository
import com.example.data.model.OwnerResponse
import com.example.data.model.RepoDetailsResponse
import com.example.domain.mapper.toRepoDetails
import com.example.test.BaseTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class GetRepoDetailsUseCaseTest : BaseTest() {

    private lateinit var getRepoDetailsUseCase: GetRepoDetailsUseCase

    private val repository: Repository = mock()
    private val errorHandler: ErrorHandler = mock()

    @Before
    fun setUp() {
        getRepoDetailsUseCase =
            GetRepoDetailsUseCase(repository, dispatcherProvider, errorHandler)
    }

    @Test
    fun `get repo details success`() {
        testCoroutineRule.runBlockingTest {

            val response = getRepoDetailsResponse()

            val params = GetRepoDetailsUseCase.Params("a", "b")
            whenever(repository.getRepoDetails(params.author, params.repoName)).thenReturn(response)

            val result = getRepoDetailsUseCase.invoke(params)

            Assert.assertFalse(result is DataResult.Failure)

            val data = (result as DataResult.Success).data
            Assert.assertNotNull(data)
            Assert.assertEquals(data.name, response.name)
            Assert.assertEquals(data, response.toRepoDetails())
        }
    }

    @Test
    fun `get repo details failure`() {
        testCoroutineRule.runBlockingTest {

            val exception = NullPointerException()
            val message = "Error"
            val params = GetRepoDetailsUseCase.Params("a", "b")

            whenever(errorHandler.handleError(exception)).thenReturn(
                DataResult.Failure(
                    DataException.UnknownException(message)
                )
            )
            whenever(repository.getRepoDetails(params.author, params.repoName)).thenThrow(exception)

            val result = getRepoDetailsUseCase.invoke(params)

            Assert.assertTrue(result is DataResult.Failure)
            val failure = result as DataResult.Failure
            Assert.assertEquals(message, failure.exception.message)
        }
    }

    private fun getRepoDetailsResponse(): RepoDetailsResponse {
        return RepoDetailsResponse(
            id = "aa",
            name = "bb",
            fullName = "aabb",
            ownerResponse = OwnerResponse(
                id = "id",
                name = "name",
                avatar = "avatar"
            ),
            isPrivate = false,
            description = "desc",
            stars = 1,
            forks = 1,
            branch = "branch",
            issues = 3,
            watchers = 3,
            licenseResponse = null
        )
    }
}