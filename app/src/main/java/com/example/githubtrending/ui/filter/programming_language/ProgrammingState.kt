package com.example.githubtrending.ui.filter.programming_language

import com.example.domain.model.Language
import com.example.githubtrending.base.BaseState
import com.example.githubtrending.utils.Selectable

data class ProgrammingState(
    val languages: List<Selectable<Language>> = emptyList(),
    val filteredLanguages: List<Selectable<Language>> = emptyList()): BaseState