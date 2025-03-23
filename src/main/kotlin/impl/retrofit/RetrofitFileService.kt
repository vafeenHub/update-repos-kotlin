package impl.retrofit

import base.FileService
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitFileService : FileService {
    @GET("${Clients.NAME}/{repoName}/main/{fileName}") // Указываем путь до файла
    override suspend fun getRawContent(
        @Path("repoName") repoName: String, // repoName
        @Path("fileName") fileName: String // fileName
    ): String?
}