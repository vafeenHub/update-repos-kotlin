import com.sun.jdi.Value
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val repoMap = mutableMapOf<String, MutableList<GitHubRepo>>()
    RepoInfoWithReadmeRepository().getInfo().forEach { repo ->
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
    repoMap.keys.sortedAsSemesters().forEach { key ->
        println("\nkey = $key")
        repoMap[key]?.forEach { repo ->
            println(repo.getLinkedString())
        }
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

fun GitHubRepo.getLinkedString(): String {
    return "[${
        readme?.replace("#", "")?.trim()
    }]($html_url)"
}