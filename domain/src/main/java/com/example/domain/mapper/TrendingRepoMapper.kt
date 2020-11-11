package com.example.domain.mapper

import com.example.data.model.RepoResponse
import com.example.domain.model.TrendingRepo

fun RepoResponse.toTrendingRepo(): TrendingRepo {
    return TrendingRepo(
        author = author,
        name = name,
        avatar = avatar,
        url = url,
        description = description,
        language = language.orEmpty(),
        languageColor = languageColor.orEmpty(),
        stars = stars,
        forks = forks,
        currentPeriodStars = currentPeriodStars,
        builtBys = builtBys.map { it.toBuiltBy() }
    )
}