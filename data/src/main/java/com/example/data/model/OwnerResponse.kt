package com.example.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OwnerResponse(
    @Json(name = "id")
    val id: String,
    @Json(name = "login")
    val name: String,
    @Json(name = "avatar_url")
    val avatar: String,
)