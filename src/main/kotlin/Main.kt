import base.Client
import impl.retrofit.RetrofitModule
import impl.retrofit.RetrofitName
import kotlinx.coroutines.runBlocking
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.getKoin
import readme_processor.*


fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("Please provide an account name as a command-line argument.")
        return
    }
    val accountName = args[0]

    startKoin {
        modules(RetrofitModule)
    }
    runBlocking {
        val repoMap = repoMapOf()

        val repository = RepoInfoWithReadmeRepository(getKoin().get<Client>(named(RetrofitName)))
        repository.getInfo(accountName)
            .forEach { repo ->
                if (repo.name != ".github") {
                    println(repo)
                    repo.getSemesters().let { semesters ->
                        if (semesters == null) {
                            repoMap.add("others", repo)
                        } else {
                            semesters.forEach { semester ->
                                if (semester.isNotBlank()) repoMap.add(semester, repo)
                            }
                        }
                    }
                }
            }

        ReadmeProcessor(repoMap, FileRepoMapProcessor).process(getContentFromTemplateReadme())
        repository.closeConnection()
    }
}

fun GitHubRepo.getSemesters(): List<String>? = if (name.contains("semester"))
    name.substringAfter("_").substringBefore("semester").split("-").filter { it.isNotBlank() }
else null



