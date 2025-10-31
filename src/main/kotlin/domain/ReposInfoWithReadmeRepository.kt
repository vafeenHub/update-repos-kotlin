package domain

import domain.models.GitHubRepo
import kotlinx.coroutines.flow.Flow

/**
 * Репозиторий для получения расширенной информации о репозиториях, включая содержимое их README-файлов.
 *
 * Определяет контракт для обогащения базовой информации о репозиториях
 * данными из их README.md файлов.
 */
interface ReposInfoWithReadmeRepository {
    /**
     * Получает информацию о репозиториях и дополняет её содержимым README файла в виде асинхронного потока.
     *
     * @param accountName Имя учетной записи пользователя GitHub.
     * @param filterPredicate Предикат для фильтрации репозиториев перед обработкой.
     * @return Асинхронный поток [Flow] с объектами [GitHubRepo], обогащенными содержимым их README-файлов.
     */
    suspend fun getInfo(
        accountName: String,
        filterPredicate: (GitHubRepo) -> Boolean
    ): Flow<GitHubRepo>
}