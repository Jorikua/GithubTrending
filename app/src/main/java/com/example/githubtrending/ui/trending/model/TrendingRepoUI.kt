package com.example.githubtrending.ui.trending.model

import android.text.SpannableStringBuilder
import androidx.core.text.bold

data class TrendingRepoUI(
    val name: String,
    val author: String,
    val avatar: String?,
    val builtByAvatars: List<String?>,
    val language: String,
    val languageColor: Int,
    val description: String,
    val stars: String,
    val starsValue: Int,
    val forks: String,
    val forksValue: Int,
    val currentStars: String,
    val currentStarsValue: Int
) {

    val title: SpannableStringBuilder
        get() {
            return SpannableStringBuilder()
                .append(author)
                .append(" / ")
                .bold { append(name) }
        }
}