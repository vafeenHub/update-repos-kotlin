package base

import GitHubRepo

interface Service {
    suspend fun pagedListRepos(
        page: Int,
        perPage: Int = 100
    ): List<GitHubRepo>

}