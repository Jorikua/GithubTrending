package com.example.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RepoResponse(
    @Json(name = "author")
    val author: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "avatar")
    val avatar: String?,
    @Json(name = "url")
    val url: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "language")
    val language: String?,
    @Json(name = "languageColor")
    val languageColor: String?,
    @Json(name = "stars")
    val stars: Int,
    @Json(name = "forks")
    val forks: Int,
    @Json(name = "currentPeriodStars")
    val currentPeriodStars: Int,
    @Json(name = "builtBy")
    val builtBys: List<BuiltByResponse> = emptyList()
)