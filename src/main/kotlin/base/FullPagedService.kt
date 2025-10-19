package base

import GitHubRepo

class FullPagedService(private val service: Service) {
    suspend fun fullListRepos(accountName: String): List<GitHubRepo> {
        val allRepos = mutableListOf<GitHubRepo>()
        var page = 1
        while (true) {
            val repos = service.pagedListRepos(accountName, page = page)
            if (repos.isEmpty()) {
                break // Если страница пустая, выходим из цикла
            }
            allRepos.addAll(repos)
            page++
        }
        return allRepos
    }
}