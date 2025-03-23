import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubFileService {
    @GET("${RetrofitClient.NAME}/{repoName}/main/{fileName}") // Указываем путь до файла
    suspend fun getRawContent(
        @Path("repoName") repoName: String, // repoName
        @Path("fileName") fileName: String // fileName
    ): String
}