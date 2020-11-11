package com.example.githubtrending.utils.extensions

import com.example.base.resource.ResourceManager
import com.example.domain.model.Granularity
import com.example.githubtrending.R

fun Granularity.getHumanString(resourceManager: ResourceManager): String {
    return when(this) {
        Granularity.DAILY -> resourceManager.getString(R.string.today)
        Granularity.WEEKLY -> resourceManager.getString(R.string.this_week)
        Granularity.MONTHLY -> resourceManager.getString(R.string.this_month)
    }
}