package com.example.githubtrending.ui.filter.granularity

import androidx.lifecycle.viewModelScope
import com.example.base.coroutines.CoroutinesDispatcherProvider
import com.example.domain.model.Granularity
import com.example.githubtrending.base.BaseViewModel
import com.example.githubtrending.utils.Selectable
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GranularityViewModel(
    private val provider: CoroutinesDispatcherProvider
) : BaseViewModel<GranularityState>(GranularityState()) {

    fun getGranularities(granularity: Granularity) {
        viewModelScope.launch {
            val granularities = withContext(provider.computation) {
                Granularity.values().map {
                    Selectable(it, isSelected = it == granularity)
                }
            }
            state.value = currentState.copy(granularities = granularities)
        }
    }
}