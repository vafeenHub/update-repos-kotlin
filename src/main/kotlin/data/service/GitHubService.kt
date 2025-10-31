package data.service

import domain.models.GitHubRepo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface GitHubService {
    @GET("users/{accountName}/repos")
    suspend fun pagedListRepos(
        @Path("accountName") accountName: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 100
    ): List<GitHubRepo>

    @GET("{accountName}/{repoName}/{default_branch}/{fileName}")
    suspend fun getRawContent(
        @Path("accountName") accountName: String,
        @Path("repoName") repoName: String,
        @Path("default_branch") defaultBranch: String,
        @Path("fileName") fileName: String
    ): String?
}