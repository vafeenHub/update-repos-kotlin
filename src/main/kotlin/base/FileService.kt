package base

interface FileService {
    suspend fun getRawContent(
        accountName: String,
        repoName: String,
        defaultBranch: String,
        fileName: String
    ): String?
}