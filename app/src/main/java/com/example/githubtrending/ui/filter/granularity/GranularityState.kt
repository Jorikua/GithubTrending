package com.example.githubtrending.ui.filter.granularity

import com.example.domain.model.Granularity
import com.example.githubtrending.base.BaseState
import com.example.githubtrending.utils.Selectable

data class GranularityState(val granularities: List<Selectable<Granularity>> = emptyList()) :
    BaseState