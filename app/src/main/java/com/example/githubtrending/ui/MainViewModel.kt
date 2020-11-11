package com.example.githubtrending.ui

import androidx.lifecycle.MutableLiveData
import com.example.domain.model.Granularity
import com.example.githubtrending.base.BaseViewModel
import com.example.githubtrending.ui.model.Filter
import com.example.githubtrending.ui.model.Order

class MainViewModel : BaseViewModel<Filter>(Filter()) {

    val filter = MutableLiveData(Filter())

    private val currentFilter: Filter
        get() = filter.value ?: throw NullPointerException()

    fun onSpokenLanguageSelected(name: String, code: String?) {
        filter.value = currentFilter.copy(
            spokenLanguage = name,
            params = currentFilter.params.copy(
                spokenLanguageCode = code
            ),
            state = Filter.State.FILTER
        )
    }

    fun onProgrammingLanguageSelected(name: String, code: String?) {
        filter.value = currentFilter.copy(
            programmingLanguage = name,
            params = currentFilter.params.copy(
                programmingLanguageCode = code
            ),
            state = Filter.State.FILTER
        )
    }

    fun onGranularityLanguageSelected(granularity: Granularity) {
        filter.value = currentFilter.copy(
            params = currentFilter.params.copy(
                granularity = granularity
            ),
            state = Filter.State.FILTER
        )
    }

    fun order(itemId: Int) {
        filter.value = currentFilter.copy(
            order = Order.values().firstOrNull { it.itemId == itemId } ?: Order.STARS,
            state = Filter.State.SORT
        )
    }

    fun reset() {
        filter.value = Filter()
    }
}