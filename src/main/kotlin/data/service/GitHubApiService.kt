package data.service

import domain.models.GitHubRepo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface GitHubApiService {
    @GET("users/{accountName}/repos")
    suspend fun pagedListRepos(
        @Path("accountName") accountName: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 100
    ): List<GitHubRepo>
}