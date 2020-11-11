package com.example.githubtrending.ui.model

import androidx.annotation.StringRes
import com.example.githubtrending.R

enum class Order(val itemId: Int, @StringRes val titleResId: Int) {
    NAME(R.id.name, R.string.name),
    AUTHOR(R.id.author, R.string.author),
    STARS(R.id.stars, R.string.stars),
    FORKS(R.id.forks, R.string.forks),
    CURRENT_STARS(R.id.current_stars, R.string.current_stars)
}