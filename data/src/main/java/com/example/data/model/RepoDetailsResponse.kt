package com.example.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RepoDetailsResponse(
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "full_name")
    val fullName: String,
    @Json(name = "owner")
    val ownerResponse: OwnerResponse,
    @Json(name = "private")
    val isPrivate: Boolean,
    @Json(name = "description")
    val description: String,
    @Json(name = "stargazers_count")
    val stars: Int,
    @Json(name = "forks_count")
    val forks: Int,
    @Json(name = "default_branch")
    val branch: String,
    @Json(name = "open_issues")
    val issues: Int,
    @Json(name = "subscribers_count")
    val watchers: Int,
    @Json(name = "license")
    val licenseResponse: LicenseResponse?
)