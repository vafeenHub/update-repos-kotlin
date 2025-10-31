package data.service

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Streaming

/**
 * Интерфейс Retrofit для взаимодействия с GitHub Raw Content API.
 *
 * Определяет методы для получения сырого содержимого файлов из репозиториев,
 * используя потоковую передачу для эффективной обработки данных.
 */
internal interface GitHubRawContentService {
    /**
     * Получает сырое содержимое файла из репозитория в виде [ResponseBody].
     *
     * Аннотация [Streaming] указывает Retrofit'у не загружать весь файл в память,
     * а передавать его в виде потока, что критически важно для больших файлов.
     *
     * @param accountName Имя учетной записи пользователя GitHub.
     * @param repoName Название репозитория.
     * @param defaultBranch Название ветки по умолчанию (например, "main" или "master").
     * @param fileName Имя файла для загрузки (например, "README.md").
     * @return [ResponseBody], который можно использовать для чтения содержимого файла в виде потока.
     */
    @Streaming
    @GET("{accountName}/{repoName}/{default_branch}/{fileName}")
    suspend fun getRawContent(
        @Path("accountName") accountName: String,
        @Path("repoName") repoName: String,
        @Path("default_branch") defaultBranch: String,
        @Path("fileName") fileName: String
    ): ResponseBody
}