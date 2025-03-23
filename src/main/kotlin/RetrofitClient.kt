import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import kotlin.jvm.java

object RetrofitClient {
    private const val BASE_URL_GITHUB_SERVICE = "https://api.github.com/"
    private const val BASE_URL_GITHUB_FILE_SERVICE = "https://raw.githubusercontent.com/"
const val NAME = "vafeenLabs"
    val gitHubService: GitHubService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_GITHUB_SERVICE)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(GitHubService::class.java)
    }
    val gitHubFileService: GitHubFileService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_GITHUB_FILE_SERVICE)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build().create(GitHubFileService::class.java)
    }
}
