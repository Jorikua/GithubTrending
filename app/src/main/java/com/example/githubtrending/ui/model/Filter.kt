package com.example.githubtrending.ui.model

import com.example.base.resource.ResourceManager
import com.example.domain.model.Granularity
import com.example.domain.usecase.GetTrendingReposUseCase
import com.example.githubtrending.R
import com.example.githubtrending.base.BaseState

data class Filter(
    val order: Order = Order.STARS,
    val spokenLanguage: String = "",
    val programmingLanguage: String = "",
    val params: GetTrendingReposUseCase.Params = GetTrendingReposUseCase.Params(
        granularity = Granularity.DAILY,
        spokenLanguageCode = null,
        programmingLanguageCode = null
    ),
    val state: State = State.FILTER
): BaseState {

    fun getSpokenLanguage(resourceManager: ResourceManager): String {
        return if (spokenLanguage.isEmpty()) resourceManager.getString(R.string.all_languages) else spokenLanguage
    }

    fun getProgrammingLanguage(resourceManager: ResourceManager): String {
        return if (programmingLanguage.isEmpty()) resourceManager.getString(R.string.all_languages) else programmingLanguage
    }

    enum class State {
        FILTER, SORT
    }
}