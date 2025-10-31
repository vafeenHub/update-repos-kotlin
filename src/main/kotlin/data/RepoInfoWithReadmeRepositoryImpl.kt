package data

import domain.FileContentRepository
import domain.ReposInfoRepository
import domain.ReposInfoWithReadmeRepository
import domain.models.GitHubRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class RepoInfoWithReadmeRepositoryImpl(
    private val reposInfoRepository: ReposInfoRepository,
    private val fileContentRepository: FileContentRepository,
) : ReposInfoWithReadmeRepository {
    private val logIsApplied = false
    override suspend fun getInfo(
        accountName: String,
        filterPredicate: (GitHubRepo) -> Boolean
    ): Flow<GitHubRepo> = reposInfoRepository.getAllRepos(accountName, filterPredicate).map {
        try {
            val decodedReadme = fileContentRepository.getRawContent(
                accountName = accountName,
                repoName = it.name,
                defaultBranch = it.default_branch,
                fileName = "README.md"
            )
            if (logIsApplied) println("Repository Name: ${it.name}, README: $decodedReadme")
            if (decodedReadme != null) {
                it.copy(readmeLines = decodedReadme.lines().filter { str -> str.isNotEmpty() })
            } else it
        } catch (e: Exception) {
            if (logIsApplied) println("Repository Name: ${it.name}, ${e.stackTraceToString()}")
            it
        }
    }
}