import base.Client

class RepoInfoWithReadmeRepository(private val client: Client) {
    suspend fun getInfo(): List<GitHubRepo> {
        val result = mutableListOf<GitHubRepo>()
        val repos = client.fullPagedService.fullListRepos().also {
            println("size = ${it.size}")
        }
        repos.forEachIndexed { index, repo ->
            if (!repo.private) {
                try {
                    val decodedReadme = client.fileService.getRawContent(
                        repo.name, "README.md"
                    )?.substringBefore('\n')
                    if (decodedReadme != null) {
                        result.add(repos[index].copy(readme = decodedReadme))
                    }
                    println("Repository Name: ${repo.name}, README: $decodedReadme")
                } catch (_: Exception) {
                    println("Repository Name: ${repo.name}, ERRRRRRRRRORRRRRRRRRRRR")
                }
            }
        }
        return result
    }

    fun closeConnection() {
        client.shutdown()
    }
}