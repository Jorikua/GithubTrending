package com.example.githubtrending.ui.trending

import com.example.base.resource.ResourceManager
import com.example.domain.model.Granularity
import com.example.domain.model.TrendingRepo
import com.example.domain.usecase.GetTrendingReposUseCase
import com.example.githubtrending.R
import com.example.githubtrending.ui.model.Filter
import com.example.githubtrending.ui.trending.mapper.toTrendingRepoUI
import com.example.githubtrending.utils.extensions.getHumanString
import com.example.test.BaseTest
import com.example.test.DEFAULT_ERROR_MESSAGE
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

private const val thisMonth = "this month"
private const val allLanguages = "all languages"

@RunWith(MockitoJUnitRunner::class)
class TrendingViewModelTest : BaseTest() {

    private lateinit var viewModel: TrendingViewModel

    private val getTrendingReposUseCase: GetTrendingReposUseCase = mock()
    private val resourceManager: ResourceManager = mock()

    @Before
    fun setUp() {
        viewModel = TrendingViewModel(getTrendingReposUseCase, resourceManager)
    }

    @Test
    fun `check titles with default filter`() {
        testCoroutineRule.runBlockingTest {
            val filter = Filter()

            val repos = getRepos()
            getTrendingReposUseCase.stub {
                onBlocking { getTrendingReposUseCase.invoke(filter.params) } doReturn repos.toSuccessResult
            }

            mockResources(filter)
            viewModel.getTrendingRepos(filter)

            val state = viewModel.state.value!!

            Assert.assertEquals(state.filterState.granularity, thisMonth)
            Assert.assertEquals(state.filterState.programmingLanguage, allLanguages)
            Assert.assertEquals(state.filterState.spokenLanguage, allLanguages)
        }
    }

    @Test
    fun `check titles with edited filter`() {
        testCoroutineRule.runBlockingTest {
            val spokenLanguage = "English"
            val programmingLanguage = "Java"

            val spokenCode = "en"
            val programmingCode = "java"
            val granularity = Granularity.MONTHLY
            val filter = Filter(
                spokenLanguage = spokenLanguage,
                programmingLanguage = programmingLanguage,
                params = GetTrendingReposUseCase.Params(
                    granularity = granularity,
                    spokenLanguageCode = spokenCode,
                    programmingLanguageCode = programmingCode
                ),
                state = Filter.State.FILTER
            )

            val repos = getRepos()
            getTrendingReposUseCase.stub {
                onBlocking { getTrendingReposUseCase.invoke(filter.params) } doReturn repos.toSuccessResult
            }

            mockResources(filter)
            viewModel.getTrendingRepos(filter)

            val state = viewModel.state.value!!

            verify(getTrendingReposUseCase, times(1)).invoke(filter.params)
            Assert.assertEquals(state.filterState.granularity, thisMonth)
            Assert.assertEquals(state.filterState.programmingLanguage, programmingLanguage)
            Assert.assertEquals(state.filterState.spokenLanguage, spokenLanguage)
        }
    }

    @Test
    fun `get trending repos with default filter`() {
        testCoroutineRule.runBlockingTest {
            val filter = Filter()

            val repos = getRepos()
            getTrendingReposUseCase.stub {
                onBlocking { getTrendingReposUseCase.invoke(filter.params) } doReturn repos.toSuccessResult
            }

            mockResources(filter)
            viewModel.getTrendingRepos(filter)

            val state = viewModel.state.value!!

            verify(getTrendingReposUseCase, times(1)).invoke(filter.params)
            Assert.assertFalse(state.showPlaceholder)

            Assert.assertFalse(viewModel.isExecutingUseCase.value!!)

            Assert.assertFalse(state.scrollToTop)
            Assert.assertFalse(state.showEmptyView)
            Assert.assertEquals(state.trendings.size, repos.size)
        }
    }

    @Test
    fun `get trending repos with edited filter`() {
        testCoroutineRule.runBlockingTest {
            val spokenLanguage = "English"
            val programmingLanguage = "Java"

            val spokenCode = "en"
            val programmingCode = "java"
            val granularity = Granularity.MONTHLY
            val filter = Filter(
                spokenLanguage = spokenLanguage,
                programmingLanguage = programmingLanguage,
                params = GetTrendingReposUseCase.Params(
                    granularity = granularity,
                    spokenLanguageCode = spokenCode,
                    programmingLanguageCode = programmingCode
                ),
                state = Filter.State.FILTER
            )

            val repos = getRepos()
            getTrendingReposUseCase.stub {
                onBlocking { getTrendingReposUseCase.invoke(filter.params) } doReturn repos.toSuccessResult
            }

            mockResources(filter)

            viewModel.getTrendingRepos(filter)

            val state = viewModel.state.value!!

            verify(getTrendingReposUseCase, times(1)).invoke(filter.params)
            Assert.assertFalse(state.showPlaceholder)

            Assert.assertFalse(viewModel.isExecutingUseCase.value!!)

            Assert.assertFalse(state.scrollToTop)
            Assert.assertFalse(state.showEmptyView)
            Assert.assertEquals(state.trendings.size, repos.size)
        }
    }

    @Test
    fun `get trending repos with no results`() {
        testCoroutineRule.runBlockingTest {
            val filter = Filter()

            val repos = emptyList<TrendingRepo>()
            getTrendingReposUseCase.stub {
                onBlocking { getTrendingReposUseCase.invoke(filter.params) } doReturn repos.toSuccessResult
            }

            mockResources(filter)
            viewModel.getTrendingRepos(filter)

            val state = viewModel.state.value!!

            verify(getTrendingReposUseCase, times(1)).invoke(filter.params)
            Assert.assertFalse(state.scrollToTop)
            Assert.assertTrue(state.showEmptyView)
            Assert.assertEquals(state.trendings.size, repos.size)
        }
    }

    @Test
    fun `get trending repos error with empty repos`() {
        testCoroutineRule.runBlockingTest {
            val filter = Filter()

            val repos = emptyList<TrendingRepo>()
            getTrendingReposUseCase.stub {
                onBlocking { getTrendingReposUseCase.invoke(filter.params) } doReturn repos.toFailureResult
            }

            mockResources(filter)

            viewModel.getTrendingRepos(filter)

            val state = viewModel.state.value!!

            verify(getTrendingReposUseCase, times(1)).invoke(filter.params)
            Assert.assertTrue(state.showPlaceholder)
            Assert.assertFalse(state.showEmptyView)
        }
    }

    @Test
    fun `get trending repos error with not empty repos`() {
        testCoroutineRule.runBlockingTest {
            val filter = Filter()

            val repos = getRepos()
            getTrendingReposUseCase.stub {
                onBlocking { getTrendingReposUseCase.invoke(filter.params) } doReturn repos.toFailureResult
            }

            mockResources(filter)

            viewModel.state.value = viewModel.state.value?.copy(
                trendings = repos.map { it.toTrendingRepoUI() }
            )
            viewModel.getTrendingRepos(filter)

            val state = viewModel.state.value!!

            verify(getTrendingReposUseCase, times(1)).invoke(filter.params)
            Assert.assertFalse(state.showPlaceholder)
            Assert.assertFalse(state.showEmptyView)
            Assert.assertEquals(viewModel.exception.value?.message, DEFAULT_ERROR_MESSAGE)
        }
    }

    @Test
    fun `refresh test`() {
        testCoroutineRule.runBlockingTest {
            val filter = Filter(state = Filter.State.SORT)
            mockResources(filter)
            val repos = getRepos()
            getTrendingReposUseCase.stub {
                onBlocking { getTrendingReposUseCase.invoke(filter.params) } doReturn repos.toFailureResult
            }

            viewModel.getTrendingRepos(filter)
            Assert.assertEquals(viewModel.filter?.state, Filter.State.SORT)

            viewModel.refresh()
            Assert.assertEquals(viewModel.filter?.state, Filter.State.FILTER)
        }
    }

    @Test
    fun `spoken language selected`() {
        viewModel.onSpokenLanguageClick()
        Assert.assertEquals(viewModel.event.value, TrendingViewModel.Event.ToSpokenLanguageFilter(viewModel.filter?.params?.spokenLanguageCode))
    }

    @Test
    fun `spoken programming selected`() {
        viewModel.onProgrammingLanguageClick()
        Assert.assertEquals(viewModel.event.value, TrendingViewModel.Event.ToProgrammingLanguageFilter(viewModel.filter?.params?.programmingLanguageCode))
    }

    @Test
    fun `granularity selected`() {
        viewModel.onGranularityClick()
        Assert.assertEquals(viewModel.event.value, TrendingViewModel.Event.ToGranularityFilter(viewModel.filter?.params?.granularity ?: Granularity.DAILY))
    }

    private fun mockResources(filter: Filter) {
        whenever(filter.params.granularity.getHumanString(resourceManager)).doReturn(thisMonth)
        whenever(resourceManager.getString(R.string.all_languages)).doReturn(allLanguages)
    }

    private fun getRepos(): List<TrendingRepo> {
        return listOf(
            TrendingRepo(
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
            ),
            TrendingRepo(
                author = "c",
                name = "d",
                avatar = null,
                url = "as",
                description = "desc",
                language = "language",
                languageColor = "color",
                stars = 1,
                forks = 1,
                currentPeriodStars = 1
            )
        )
    }
}