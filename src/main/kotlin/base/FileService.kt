package base

interface FileService {
    suspend fun getRawContent(
        repoName: String,
        defaultBranch: String,
        fileName: String
    ): String?
}