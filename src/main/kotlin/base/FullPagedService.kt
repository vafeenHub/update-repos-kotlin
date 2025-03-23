package base

import GitHubRepo

class FullPagedService(private val service: Service) {
    suspend fun fullListRepos(): List<GitHubRepo> {
        val allRepos = mutableListOf<GitHubRepo>()
        var page = 1
        while (true) {
            val repos = service.pagedListRepos(page = page)
            if (repos.isEmpty()) {
                break // Если страница пустая, выходим из цикла
            }
            allRepos.addAll(repos)
            page++
        }
        return allRepos
    }
}