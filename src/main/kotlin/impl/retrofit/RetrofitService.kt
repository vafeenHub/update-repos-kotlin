package impl.retrofit

import GitHubRepo
import base.Service
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService: Service {
    @GET("users/${Clients.NAME}/repos")
    override suspend fun pagedListRepos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): List<GitHubRepo>

}