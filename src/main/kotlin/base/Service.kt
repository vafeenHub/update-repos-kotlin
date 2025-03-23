package base

import GitHubRepo

interface Service {
    suspend fun listRepos(): List<GitHubRepo>
}