package data.service

import domain.models.GitHubRepo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Интерфейс Retrofit для взаимодействия с GitHub REST API.
 *
 * Определяет методы для получения основной информации, такой как списки репозиториев.
 */
internal interface GitHubApiService {
    /**
     * Получает постраничный список репозиториев для указанной учетной записи.
     *
     * @param accountName Имя учетной записи пользователя GitHub.
     * @param page Номер страницы для пагинации.
     * @param perPage Количество репозиториев на одной странице (по умолчанию 100).
     * @return Список объектов [GitHubRepo], представляющих репозитории.
     */
    @GET("users/{accountName}/repos")
    suspend fun pagedListRepos(
        @Path("accountName") accountName: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = 100
    ): List<GitHubRepo>
}