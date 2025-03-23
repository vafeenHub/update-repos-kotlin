import kotlinx.coroutines.runBlocking
import java.io.File
import java.time.LocalDateTime
import kotlinx.coroutines.runBlocking


fun main() {
    runBlocking {
        val repoMap = mutableMapOf<String, MutableList<GitHubRepo>>()
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

//        repoMap.keys.sortedAsSemesters().forEach { key ->
//            println("\nkey = $key")
//            repoMap[key]?.forEach { repo ->
//                println(repo.getLinkedString())
//            }
//        }
        createReadme(repoMap)
        repository.closeConnection()
    }
}

fun MutableMap<String, MutableList<GitHubRepo>>.add(key: String, value: GitHubRepo) {
    if (this[key] == null)
        this[key] = mutableListOf<GitHubRepo>()
    this[key]?.add(value)
}

fun GitHubRepo.getSemesters(): List<String>? = if (name.contains("semester"))
    name.substringAfter("_").substringBefore("semester").split("-")
else null


fun Set<String>.sortedAsSemesters(): List<String> = this.sortedWith(
    compareBy(
        { !it.all { char -> char.isDigit() } }, // Сначала строки с цифрами, затем с буквами
        { it.toIntOrNull() ?: Int.MAX_VALUE }, // Сортировка цифр по значению
        { it } // Сортировка строк по алфавиту
    ))

fun GitHubRepo.getLinkedString(): String = "[${readme?.replaceFirst("#", "")?.trim()}]($html_url)"


fun createReadme(repoMap: Map<String, MutableList<GitHubRepo>>) {
    println("start create")
    val mainContent = getContentFromTemplateReadme()
    val file = File("profile/README.md")
    file.createNewFile()
    fun append(text: String) = file.appendText(text)
    fun newLine() = append("\n\n")
    file.writeText("")
    append(mainContent)
    newLine()
    append("${LocalDateTime.now()}")
    newLine()
    append("Repos:")
    newLine()
    repoMap.keys.sortedAsSemesters().forEach { key ->
        newLine()
        append("${if (key != "others") "Semester: " else ""}$key")
        newLine()
        repoMap[key]?.forEach { repo ->
            println(repo)
            append(repo.getLinkedString())
            newLine()
        }
    }
    println("end create")
}

fun getContentFromTemplateReadme(): String = File("README.md").readText()