package com.example.githubtrending.ui.filter.programming_language

import com.example.base.data.DataResult
import com.example.base.resource.ResourceManager
import com.example.domain.model.Language
import com.example.domain.usecase.GetProgrammingLanguagesUseCase
import com.example.githubtrending.R
import com.example.githubtrending.utils.Selectable
import com.example.test.BaseTest
import com.example.test.toFailureResult
import com.example.test.toSuccessResult
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.stub
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

private const val allLanguages = "all languages"

@RunWith(MockitoJUnitRunner::class)
class ProgrammingViewModelTest : BaseTest() {

    private lateinit var viewModel: ProgrammingViewModel

    private val getProgrammingLanguagesUseCase: GetProgrammingLanguagesUseCase = mock()
    private val resourceManager: ResourceManager = mock()

    @Before
    fun init() {
        viewModel = ProgrammingViewModel(getProgrammingLanguagesUseCase, dispatcherProvider, resourceManager)
    }

    @Test
    fun `get languages success`() {
        testCoroutineRule.runBlockingTest {
            val languages = listOf(Language("a", "b"), Language("c", "d"))

            getProgrammingLanguagesUseCase.stub {
                onBlocking { getProgrammingLanguagesUseCase.invoke(Unit) } doReturn languages.toSuccessResult
            }

            whenever(resourceManager.getString(R.string.all_languages)).thenReturn(allLanguages)

            viewModel.getLanguages(languages[0].code)

            val state = viewModel.state.value!!

            verify(getProgrammingLanguagesUseCase, times(1)).invoke(Unit)
            Assert.assertEquals(state.languages[0], Selectable(Language(allLanguages, null), false))
            Assert.assertEquals(state.languages[1], Selectable(languages[0], true))
            Assert.assertEquals(state.languages[2], Selectable(languages[1], false))

        }
    }

    @Test
    fun `get languages failure`() {
        testCoroutineRule.runBlockingTest {
            val languages = listOf(Language("a", "b"), Language("c", "d"))

            val failure = languages.toFailureResult

            getProgrammingLanguagesUseCase.stub {
                onBlocking { getProgrammingLanguagesUseCase.invoke(Unit) } doReturn failure
            }

            viewModel.getLanguages(languages[0].code)

            verify(getProgrammingLanguagesUseCase, times(1)).invoke(Unit)
            Assert.assertEquals(
                viewModel.exception.value,
                (failure as DataResult.Failure).exception
            )
        }
    }

    @Test
    fun `filter test`() {
        testCoroutineRule.runBlockingTest {

            val initialList =
                listOf(Language("a", "b"), Language("c", "d")).map { Selectable(it, false) }
            viewModel.state.value = viewModel.state.value?.copy(
                languages = initialList,
                filteredLanguages = initialList
            )

            val char1 = "a"
            viewModel.searchText.value = char1

            val state = viewModel.state.value!!

            Assert.assertEquals(state.filteredLanguages, initialList.filter { it.item.name.startsWith(char1, true) })
        }
    }
}