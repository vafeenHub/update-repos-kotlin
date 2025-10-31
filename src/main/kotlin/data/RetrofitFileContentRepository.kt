package data

import data.service.GitHubRawContentService
import domain.FileContentRepository

/**
 * Реализация [FileContentRepository], использующая Retrofit для получения сырого содержимого файлов.
 *
 * Этот класс отвечает за загрузку содержимого файлов, таких как README.md, из репозиториев GitHub,
 * используя потоковую передачу для эффективной обработки больших файлов.
 *
 * @property gitHubRawContentService Сервис Retrofit для взаимодействия с GitHub Raw Content API.
 */
internal class RetrofitFileContentRepository(
    private val gitHubRawContentService: GitHubRawContentService
) : FileContentRepository {
    private val logsIaApplied = false

    /**
     * Получает сырое текстовое содержимое файла из указанного репозитория.
     *
     * Метод использует потоковую передачу для безопасной загрузки файлов большого размера.
     * В случае ошибки при загрузке или обработке, метод вернет null.
     *
     * @param accountName Имя учетной записи пользователя GitHub.
     * @param repoName Название репозитория.
     * @param defaultBranch Название ветки по умолчанию (например, "main" или "master").
     * @param fileName Имя файла для загрузки (например, "README.md").
     * @return Содержимое файла в виде [String] или null в случае ошибки.
     */
    override suspend fun getRawContent(
        accountName: String,
        repoName: String,
        defaultBranch: String,
        fileName: String
    ): String? = try {
        val responseBody = gitHubRawContentService.getRawContent(
            accountName = accountName,
            repoName = repoName,
            defaultBranch = defaultBranch,
            fileName = fileName
        )
        responseBody.use {
            // Конвертируем поток в строку
            it.charStream().readText()
        }
    } catch (e: Exception) {
        if (logsIaApplied) println("Error ReadmeContent for repository: $repoName ${e.stackTraceToString()}")
        null
    }
}