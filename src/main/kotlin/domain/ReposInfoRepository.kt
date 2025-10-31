package domain

import domain.models.GitHubRepo
import kotlinx.coroutines.flow.Flow

interface ReposInfoRepository {
    fun getAllRepos(accountName: String, filterPredicate: (GitHubRepo) -> Boolean): Flow<GitHubRepo>
}