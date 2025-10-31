package domain

import domain.models.GitHubRepo
import kotlinx.coroutines.flow.Flow

/**
 * Репозиторий для получения базовой информации о репозиториях.
 *
 * Определяет контракт для получения списка репозиториев пользователя
 * в виде асинхронного потока данных [Flow].
 */
interface ReposInfoRepository {
    /**
     * Получает все репозитории для указанной учетной записи в виде потока данных.
     *
     * @param accountName Имя учетной записи пользователя GitHub.
     * @param filterPredicate Предикат для фильтрации репозиториев.
     * @return [Flow] с отфильтрованными репозиториями [GitHubRepo].
     */
    fun getAllRepos(accountName: String, filterPredicate: (GitHubRepo) -> Boolean): Flow<GitHubRepo>
}