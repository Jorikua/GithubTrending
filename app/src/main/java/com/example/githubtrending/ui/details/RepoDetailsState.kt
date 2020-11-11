package com.example.githubtrending.ui.details

import com.example.githubtrending.base.BaseState

data class RepoDetailsState(
    val avatarUrl: String = "",
    val author: String = "",
    val fullName: String = "",
    val name: String = "",
    val desc: String = "",
    val stars: String = "",
    val forks: String = "",
    val issues: String = "",
    val watchers: String = "",
    val license: String = "",
    val currentBranch: String = "",
    val isPrivate: Boolean = true
): BaseState