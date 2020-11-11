package com.example.domain.mapper

import com.example.data.model.RepoDetailsResponse
import com.example.domain.model.RepoDetails

fun RepoDetailsResponse.toRepoDetails(): RepoDetails {
    return RepoDetails(
        id = id,
        name = name,
        fullName = fullName,
        owner = ownerResponse.toOwner(),
        isPrivate = isPrivate,
        description = description,
        stars = stars,
        forks = forks,
        branch = branch,
        license = licenseResponse?.toLicense(),
        watchers = watchers,
        issues = issues
    )
}