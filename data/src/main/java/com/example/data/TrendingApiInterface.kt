package com.example.data

import com.example.data.model.RepoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TrendingApiInterface {

    @GET("repositories")
    suspend fun getRepositories(
        @Query("since") granularity: String,
        @Query("language") programmingLanguage: String?,
        @Query("spoken_language_code") spokenLanguage: String?
    ): List<RepoResponse>
}