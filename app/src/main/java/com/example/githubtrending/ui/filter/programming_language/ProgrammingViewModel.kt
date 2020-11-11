package com.example.githubtrending.ui.filter.programming_language

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.example.base.CoroutinesDispatcherProvider
import com.example.base.resource.ResourceManager
import com.example.domain.model.Language
import com.example.domain.usecase.GetProgrammingLanguagesUseCase
import com.example.githubtrending.R
import com.example.githubtrending.base.BaseViewModel
import com.example.githubtrending.utils.Selectable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProgrammingViewModel(
    private val getProgrammingLanguagesUseCase: GetProgrammingLanguagesUseCase,
    private val provider: CoroutinesDispatcherProvider,
    private val resourceManager: ResourceManager
) : BaseViewModel<ProgrammingState>(ProgrammingState()) {

    val searchText = MutableLiveData("")

    init {
        viewModelScope.launch {
            searchText.asFlow()
                .drop(1)
                .map { search ->
                    currentState.languages.filter {
                        it.item.name.startsWith(
                            search,
                            true
                        )
                    }
                }
                .flowOn(provider.computation)
                .collect { filteredList ->
                    state.value = currentState.copy(
                        filteredLanguages = filteredList
                    )
                }
        }
    }

    fun getLanguages(selectedLanguageCode: String?) {
        viewModelScope.launch {
            getProgrammingLanguagesUseCase.invoke(Unit).handleSuccessSuspend { languages ->
                val modifiedLanguages = withContext(provider.computation) {
                    languages
                        .toMutableList().apply {
                            val language =
                                Language(resourceManager.getString(R.string.all_languages), null)
                            add(0, language)
                        }
                        .map { Selectable(it, it.code == selectedLanguageCode) }
                }

                state.value = currentState.copy(
                    languages = modifiedLanguages,
                    filteredLanguages = modifiedLanguages
                )
            }
        }
    }
}