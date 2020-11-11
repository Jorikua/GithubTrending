package com.example.data

import com.example.data.model.LanguageResponse
import com.example.data.model.RepoDetailsResponse
import com.example.data.model.RepoResponse
import com.example.data.source.LocalDataSource

class Repository(
    private val localDataSource: LocalDataSource,
    private val trendingApiInterface: TrendingApiInterface,
    private val githubApiInterface: GithubApiInterface
) {

    suspend fun getTrendingRepos(
        granularity: String,
        programmingLanguage: String?,
        spokenLanguage: String?
    ): List<RepoResponse> {
        return trendingApiInterface.getRepositories(
            granularity,
            programmingLanguage,
            spokenLanguage
        )
    }

    fun getSpokenLanguages(): List<LanguageResponse> {
        return localDataSource.getSpokenLanguages()
    }

    fun getProgrammingLanguages(): List<LanguageResponse> {
        return localDataSource.getProgrammingLanguages()
    }

    suspend fun getRepoDetails(author: String, repoName: String): RepoDetailsResponse {
        return githubApiInterface.getRepoDetails(author, repoName)
    }
}