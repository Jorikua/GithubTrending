package com.example.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LicenseResponse(
    @Json(name = "key",)
    val key: String,
    @Json(name = "spdx_id")
    val name: String,
    @Json(name = "url")
    val url: String?
)