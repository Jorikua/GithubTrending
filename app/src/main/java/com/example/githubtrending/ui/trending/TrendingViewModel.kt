package com.example.githubtrending.ui.trending

import androidx.lifecycle.viewModelScope
import com.example.base.data.DataResult
import com.example.base.resource.ResourceManager
import com.example.domain.model.Granularity
import com.example.domain.usecase.GetTrendingReposUseCase
import com.example.githubtrending.base.BaseViewModel
import com.example.githubtrending.ui.model.Filter
import com.example.githubtrending.ui.model.Order
import com.example.githubtrending.ui.trending.mapper.toTrendingRepoUI
import com.example.githubtrending.ui.trending.model.TrendingRepoUI
import com.example.githubtrending.utils.extensions.getHumanString
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.launch

class TrendingViewModel(
    private val getTrendingReposUseCase: GetTrendingReposUseCase,
    private val resourceManager: ResourceManager
) : BaseViewModel<TrendingState>(TrendingState()) {

    val event = LiveEvent<Event>()

    var filter: Filter? = null
        //for testing
        private set

    fun getTrendingRepos(filter: Filter) {
        this.filter = filter

        state.value = currentState.copy(
            filterState = currentState.filterState.copy(
                programmingLanguage = filter.getProgrammingLanguage(resourceManager),
                spokenLanguage = filter.getSpokenLanguage(resourceManager),
                granularity = filter.params.granularity.getHumanString(resourceManager)
            ),
            showPlaceholder = false
        )

        when (filter.state) {
            Filter.State.FILTER -> loadRepos(filter)
            Filter.State.SORT -> sortRepos(filter.order)
        }
    }

    private fun loadRepos(filter: Filter) {
        isLoading(true)
        viewModelScope.launch {
            val result = getTrendingReposUseCase.invoke(filter.params)
            isLoading(false)

            if (result is DataResult.Failure) {
                showFailure(result)
                return@launch
            }

            val repos = (result as DataResult.Success).data
            state.value = currentState.copy(
                trendings = repos.map { it.toTrendingRepoUI() }.sortRepos(filter.order),
                showEmptyView = repos.isEmpty(),
                scrollToTop = false
            )
        }
    }

    private fun List<TrendingRepoUI>.sortRepos(order: Order?): List<TrendingRepoUI> {
        return when (order) {
            Order.NAME -> sortedBy { it.name }
            Order.STARS -> sortedByDescending { it.starsValue }
            Order.FORKS -> sortedByDescending { it.forksValue }
            Order.CURRENT_STARS -> sortedByDescending { it.currentStarsValue }
            Order.AUTHOR -> sortedByDescending { it.author }
            else -> throw NullPointerException("Unknown order $order")
        }
    }

    private fun sortRepos(order: Order) {
        state.value = currentState.copy(
            trendings = currentState.trendings.sortRepos(order),
            scrollToTop = true
        )
    }

    private fun showFailure(result: DataResult.Failure) {
        val trendingsAreEmpty = currentState.trendings.isEmpty()
        state.value = currentState.copy(
            showPlaceholder = trendingsAreEmpty,
            showEmptyView = false
        )
        if (!trendingsAreEmpty) {
            exception.value = result.exception
        }
    }

    fun refresh() {
        filter = filter?.copy(
            state = Filter.State.FILTER
        )
        filter?.let {
            getTrendingRepos(it)
        }
    }

    fun onSpokenLanguageClick() {
        event.value = Event.ToSpokenLanguageFilter(filter?.params?.spokenLanguageCode)
    }

    fun onProgrammingLanguageClick() {
        event.value = Event.ToProgrammingLanguageFilter(filter?.params?.programmingLanguageCode)
    }

    fun onGranularityClick() {
        event.value = Event.ToGranularityFilter(filter?.params?.granularity ?: Granularity.DAILY)
    }

    sealed class Event {
        data class ToSpokenLanguageFilter(val spokenLanguageCode: String?) : Event()
        data class ToProgrammingLanguageFilter(val programmingLanguageCode: String?) : Event()
        data class ToGranularityFilter(val granularity: Granularity) : Event()
    }
}