package impl.ktor

import GitHubRepo
import base.FileService
import io.ktor.client.HttpClient
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode

class KtorGitHubFileService(private val client: HttpClient) : FileService {
    override suspend fun getRawContent(repoName: String, defaultBranch: String, fileName: String): String? {
        val result = client.get {
            url("${Clients.BASE_URL_GITHUB_FILE_SERVICE}${Clients.NAME}/${repoName}/${defaultBranch}/$fileName")
            accept(ContentType.Text.Plain)
        }
        return if (result.status == HttpStatusCode.Companion.OK)
            result.bodyAsText()
        else null
    }
}