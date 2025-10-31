package domain

interface FileContentRepository {
    suspend fun getRawContent(
        accountName: String,
        repoName: String,
        defaultBranch: String,
        fileName: String
    ): String?
}