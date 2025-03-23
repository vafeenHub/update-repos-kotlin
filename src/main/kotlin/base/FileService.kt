package base

interface FileService {
    suspend fun getRawContent(repoName: String, fileName: String): String?
}