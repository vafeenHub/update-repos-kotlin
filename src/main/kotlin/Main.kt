import kotlinx.coroutines.runBlocking
import readme_processor.*


fun main() {
    runBlocking {
        val repoMap = repoMapOf()

        val repository = RepoInfoWithReadmeRepository(Clients.retrofitClient)
        repository.getInfo()
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



