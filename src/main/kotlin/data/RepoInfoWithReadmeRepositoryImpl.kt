package data

import domain.FileContentRepository
import domain.ReposInfoRepository
import domain.ReposInfoWithReadmeRepository
import domain.models.GitHubRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Реализация [ReposInfoWithReadmeRepository], обогащающая информацию о репозиториях содержимым их README файлов.
 *
 * Этот класс получает базовую информацию о репозиториях от [reposInfoRepository],
 * а затем для каждого репозитория загружает содержимое файла README.md с помощью [fileContentRepository].
 *
 * @property reposInfoRepository Репозиторий для получения списка репозиториев.
 * @property fileContentRepository Репозиторий для загрузки содержимого файлов.
 */
internal class RepoInfoWithReadmeRepositoryImpl(
    private val reposInfoRepository: ReposInfoRepository,
    private val fileContentRepository: FileContentRepository,
) : ReposInfoWithReadmeRepository {
    private val logIsApplied = false

    /**
     * Получает информацию о репозиториях и дополняет её содержимым README файла.
     *
     * Метод создает поток репозиториев, и для каждого из них асинхронно загружает
     * и добавляет содержимое файла README.md. В случае ошибки загрузки README, репозиторий
     * возвращается в исходном виде.
     *
     * @param accountName Имя учетной записи пользователя GitHub.
     * @param filterPredicate Предикат для фильтрации репозиториев.
     * @return [Flow] с репозиториями [GitHubRepo], дополненными содержимым README.
     */
    override suspend fun getInfo(
        accountName: String,
        filterPredicate: (GitHubRepo) -> Boolean
    ): Flow<GitHubRepo> = reposInfoRepository.getAllRepos(accountName, filterPredicate).map {
        try {
            val decodedReadme = fileContentRepository.getRawContent(
                accountName = accountName,
                repoName = it.name,
                defaultBranch = it.default_branch,
                fileName = "README.md"
            )
            if (logIsApplied) println("Repository Name: ${it.name}, README: $decodedReadme")
            if (decodedReadme != null) {
                it.copy(readmeLines = decodedReadme.lines().filter { str -> str.isNotEmpty() })
            } else it
        } catch (e: Exception) {
            if (logIsApplied) println("Repository Name: ${it.name}, ${e.stackTraceToString()}")
            it
        }
    }
}