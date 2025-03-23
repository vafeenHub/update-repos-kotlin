package impl.retrofit

import GitHubRepo
import base.Service
import retrofit2.http.GET

interface RetrofitService: Service {
    @GET("users/${Clients.NAME}/repos")
    override suspend fun listRepos(
//        @Query("page") page: Int,
//        @Query("per_page") perPage: Int = 100
    ): List<GitHubRepo>

}