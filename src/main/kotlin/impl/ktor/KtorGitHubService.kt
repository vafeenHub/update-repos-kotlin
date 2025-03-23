package impl.ktor

import GitHubRepo
import base.Service
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.ktor.client.HttpClient
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
class KtorGitHubService(private val client: HttpClient) : Service {
    private val gson = Gson()

    override suspend fun pagedListRepos(
        page: Int,
        perPage: Int
    ): List<GitHubRepo> {
        val jsonString = client.get {
            url("${Clients.BASE_URL_GITHUB_SERVICE}users/${Clients.NAME}/repos")
            parameter("page", page) // Добавляем параметр page
            parameter("per_page", perPage) // Добавляем параметр per_page
            accept(ContentType.Application.Json)
        }.bodyAsText()

        // Десериализация JSON-строки в List<GitHubRepo>
        val type = object : TypeToken<List<GitHubRepo>>() {}.type
        val result: List<GitHubRepo> = gson.fromJson(jsonString, type)
        return result
    }
}