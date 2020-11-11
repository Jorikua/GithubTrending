package com.example.githubtrending.ui.trending

import com.example.githubtrending.base.BaseState
import com.example.githubtrending.ui.trending.model.TrendingRepoUI

data class TrendingState(
    val filterState: FilterState = FilterState(),
    val trendings: List<TrendingRepoUI> = emptyList(),
    val showPlaceholder: Boolean = false,
    val showEmptyView: Boolean = false,
    val scrollToTop: Boolean = false
): BaseState {
    data class FilterState(
        val programmingLanguage: String = "",
        val granularity: String = "",
        val spokenLanguage: String = ""
    )
}