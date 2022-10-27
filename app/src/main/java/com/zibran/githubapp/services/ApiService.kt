package com.zibran.githubapp.services

import com.zibran.githubapp.model.RepoIssueModel
import com.zibran.githubapp.model.RepoModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Url

interface ApiService {

    @Headers(
        "Accept: application/vnd.github+json",
        "Authorization: Bearer  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"
    )
    @GET("users/Zibran1999/repos")
    fun getAllRepository(): Call<List<RepoModel>>

    @Headers(
        "Accept: application/vnd.github+json",
        "Authorization: Bearer  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"
    )
    @GET
    fun getRepoIssues(@Url repoName: String): Call<List<RepoIssueModel>>

}