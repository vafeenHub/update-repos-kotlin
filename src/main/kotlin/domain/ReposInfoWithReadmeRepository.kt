package domain

import domain.models.GitHubRepo
import kotlinx.coroutines.flow.Flow

interface ReposInfoWithReadmeRepository {
    suspend fun getInfo(
        accountName: String,
        filterPredicate: (GitHubRepo) -> Boolean
    ): Flow<GitHubRepo>
}