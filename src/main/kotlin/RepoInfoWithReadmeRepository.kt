class RepoInfoWithReadmeRepository {
    suspend fun getInfo(): List<GitHubRepo> {
        val result = mutableListOf<GitHubRepo>()
        val repos = RetrofitClient.gitHubService.listRepos(page = 1)
        repos.forEachIndexed { index, repo ->
            if (!repo.private) {
                try {
                    val decodedReadme = RetrofitClient.gitHubFileService.getRawContent(
                        repo.name, "README.md"
                    ).substringBefore('\n')
                    result.add(repos[index].copy(readme = decodedReadme))
                    println("Repository Name: ${repo.name}, URL: ${repo.html_url}, README: $decodedReadme")
                } catch (e: Exception) {
                    println("${repo.name} ${e.message}")
                }
            }
        }
        return result
    }
}