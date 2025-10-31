package data

import data.service.GitHubApiService
import domain.ReposInfoRepository
import domain.models.GitHubRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Реализация [ReposInfoRepository], использующая Retrofit для получения информации о репозиториях.
 *
 * Этот класс запрашивает постраничные списки репозиториев из GitHub API и преобразует их в Flow.
 *
 * @property service Сервис Retrofit для взаимодействия с GitHub API.
 */
internal class RetrofitReposInfoRepository(private val service: GitHubApiService) :
    ReposInfoRepository {

    /**
     * Получает все репозитории для указанной учетной записи в виде потока данных.
     *
     * Метод последовательно запрашивает страницы с репозиториями до тех пор,
     * пока не будут получены все доступные репозитории, удовлетворяющие предикату.
     *
     * @param accountName Имя учетной записи пользователя GitHub.
     * @param filterPredicate Предикат для фильтрации репозиториев.
     * @return [Flow] с репозиториями [GitHubRepo].
     */
    override fun getAllRepos(
        accountName: String,
        filterPredicate: (GitHubRepo) -> Boolean
    ): Flow<GitHubRepo> = flow {
        var page = 1
        while (true) {
            val repos = service.pagedListRepos(accountName, page = page)
                .filter(filterPredicate)
            repos.forEach { emit(it) }
            if (repos.isEmpty()) break // Если страница пустая, выходим из цикла
            page++
        }
    }
}