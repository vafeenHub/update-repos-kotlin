package readme_processor

import GitHubRepo
import java.io.File
import java.time.LocalDateTime

typealias RepoMap = MutableMap<String, MutableList<GitHubRepo>>

fun repoMapOf(): RepoMap = mutableMapOf()
class ReadmeProcessor(private val repoMap: RepoMap, private val repoMapProcessor: RepoMapProcessor) {
    fun process(startOfReadme: String) {
        println("start create")
        repoMapProcessor.apply {
            clear()
            append(startOfReadme)
            newLine()
            append("${LocalDateTime.now()}")
            newLine()
            append("Repos:")
            newLine()
            repoMap.keys.sortedAsSemesters().forEach { key ->
                append("${if (key != "others") "Semester: " else ""}$key")
                newLine()
                repoMap[key]?.forEach { repo ->
                    println(repo)
                    append(repo.getLinkedString())
                    newLine()
                }
            }
        }
        println("end create")
    }
}

fun MutableMap<String, MutableList<GitHubRepo>>.add(key: String, value: GitHubRepo) {
    if (this[key] == null)
        this[key] = mutableListOf<GitHubRepo>()
    this[key]?.add(value)
}


fun getContentFromTemplateReadme(): String = File("README.md").readText()
fun Set<String>.sortedAsSemesters(): List<String> = this.sortedWith(
    compareBy(
        { !it.all { char -> char.isDigit() } }, // Сначала строки с цифрами, затем с буквами
        { it.toIntOrNull() ?: Int.MAX_VALUE }, // Сортировка цифр по значению
        { it } // Сортировка строк по алфавиту
    ))

fun GitHubRepo.getLinkedString(): String = "[${readme?.replaceFirst("#", "")?.trim()}]($html_url)"