package com.example.domain.model

data class RepoDetails(
    val id: String,
    val name: String,
    val fullName: String,
    val owner: Owner,
    val isPrivate: Boolean,
    val description: String,
    val stars: Int,
    val forks: Int,
    val branch: String,
    val license: License?,
    val watchers: Int,
    val issues: Int
)