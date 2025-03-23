import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubService {
    @GET("users/${RetrofitClient.NAME}/repos")
    suspend fun listRepos(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 100
    ): List<GitHubRepo>

}
