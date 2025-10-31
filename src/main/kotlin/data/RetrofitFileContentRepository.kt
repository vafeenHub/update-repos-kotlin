package data

import data.service.GitHubRawContentService
import domain.FileContentRepository

internal class RetrofitFileContentRepository(
    private val gitHubRawContentService: GitHubRawContentService
) : FileContentRepository {
    private val logsIaApplied = false
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