package data

import data.service.GitHubApiService
import domain.ReposInfoRepository
import domain.models.GitHubRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class RetrofitReposInfoRepository(private val service: GitHubApiService) :
    ReposInfoRepository {
    override fun getAllRepos(
        accountName: String,
        filterPredicate: (GitHubRepo) -> Boolean
    ): Flow<GitHubRepo> = flow {
        var page = 1
        while (true) {
            val repos = service.pagedListRepos(accountName, page = page)
                .filter(filterPredicate)
            repos.forEach { emit(it) }
            if (repos.isEmpty()) break // Если страница пустая, выходим из цикла
            page++
        }
    }
}