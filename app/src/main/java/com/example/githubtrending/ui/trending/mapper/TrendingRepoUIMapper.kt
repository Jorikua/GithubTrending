package com.example.githubtrending.ui.trending.mapper

import android.graphics.Color
import com.example.domain.model.TrendingRepo
import com.example.githubtrending.ui.trending.model.TrendingRepoUI
import com.example.githubtrending.utils.intFormat

fun TrendingRepo.toTrendingRepoUI(): TrendingRepoUI {

    val color = try {
        Color.parseColor(languageColor)
    } catch (e: Exception) {
        Color.BLUE
    }

    return TrendingRepoUI(
        name = name,
        author = author,
        avatar = avatar,
        builtByAvatars = builtBys.map { it.avatar },
        language = language,
        languageColor = color,
        description = description,
        stars = intFormat.format(stars),
        starsValue = stars,
        forks = intFormat.format(forks),
        forksValue = forks,
        currentStars = intFormat.format(currentPeriodStars),
        currentStarsValue = currentPeriodStars
    )
}