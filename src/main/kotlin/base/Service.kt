package base

import GitHubRepo

interface Service {
    suspend fun pagedListRepos(
        accountName: String,
        page: Int,
        perPage: Int = 100
    ): List<GitHubRepo>

}