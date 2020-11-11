package com.example.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BuiltByResponse(
    @Json(name = "username")
    val username: String,
    @Json(name = "href")
    val href: String,
    @Json(name = "avatar")
    val avatar: String?
)