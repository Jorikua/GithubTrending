package com.example.data

import com.example.data.model.RepoDetailsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApiInterface {

    @GET("repos/{owner}/{repo}")
    suspend fun getRepoDetails(
        @Path("owner") author: String,
        @Path("repo") repoName: String
    ): RepoDetailsResponse
}