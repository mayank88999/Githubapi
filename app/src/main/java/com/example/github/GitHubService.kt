package com.example.github

import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubService {
    @Headers(
        "Accept: application/vnd.github+json",
        "X-GitHub-Api-Version: 2022-11-28",
        "Authorization: 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"
    )
    @GET("repos/mayank88999/project/issues?state=closed")
    fun getClosedIssues(): Call<List<IssueItem>>
}
