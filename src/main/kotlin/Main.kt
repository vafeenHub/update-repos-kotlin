import data.service.AppUpdateReposModule
import data.service.client.base.Client
import domain.ReposInfoWithReadmeRepository
import domain.models.semesters
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.filter
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin
import readme_processor.FileRepoMapProcessor
import readme_processor.add
import readme_processor.getContentFromTemplateReadme
import readme_processor.processReadme
import readme_processor.repoMapOf


suspend fun main(args: Array<String>) = coroutineScope {

    var accountName: String? = null
    var i = 0
    while (i < args.size) {
        when (args[i]) {
            "-accountName", "-ac" -> {
                if (i + 1 < args.size) {
                    accountName = args[i + 1]
                    i++
                }
            }
        }
        i++
    }

    if (accountName == null) {
        println("Please provide an account name using -accountName <name> or -ac <name>")
        return@coroutineScope
    }
    startKoin {
        modules(AppUpdateReposModule)
    }
    val koin = getKoin()
    val reposInfoWithReadmeRepository = koin.get<ReposInfoWithReadmeRepository>()

    val repoMap = repoMapOf()

    reposInfoWithReadmeRepository.getInfo(
        accountName = accountName,
        filterPredicate = { it.name != ".github" && !it.private })
        .filter { it.readmeLines != null }
        .collect { repo ->
            println("repo in foreach: ${repo.name}, README: ${repo.readmeLines?.firstOrNull()}")
            val semesters = repo.semesters
            if (semesters == null) {
                repoMap.add("others", repo)
            } else {
                semesters.forEach { repoMap.add(it, repo) }
            }
        }

    processReadme(
        repoMap = repoMap,
        repoMapProcessor = FileRepoMapProcessor,
        startOfReadme = getContentFromTemplateReadme(),
    )
    val client = koin.get<Client>()
    client.closeConnection()
}
