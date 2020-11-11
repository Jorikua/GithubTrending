package com.example.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class LanguageResponse(
    @Json(name = "urlParam")
    val code: String,
    @Json(name = "name")
    val name: String
)