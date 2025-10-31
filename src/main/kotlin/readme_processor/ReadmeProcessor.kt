package readme_processor


import domain.models.GitHubRepo
import java.io.File
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

typealias RepoMap = MutableMap<String, MutableList<GitHubRepo>>

fun repoMapOf(): RepoMap = mutableMapOf()

fun processReadme(
    repoMap: RepoMap,
    repoMapProcessor: RepoMapProcessor,
    startOfReadme: String
) {
    println("start create")
    repoMapProcessor.apply {
        clear()
        append(startOfReadme)
        newLine()
        append("# Last update: ${getCurrentMoscowTimeAsString()}")
        newLine()
        append("# Repos:")
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

fun MutableMap<String, MutableList<GitHubRepo>>.add(key: String, value: GitHubRepo) {
    if (this[key] == null)
        this[key] = mutableListOf()
    this[key]?.add(value)
}


fun getContentFromTemplateReadme(): String = File("README.md").readText()
fun Set<String>.sortedAsSemesters(): List<String> = this.sortedWith(
    compareBy(
        { !it.all { char -> char.isDigit() } }, // Сначала строки с цифрами, затем с буквами
        { it.toIntOrNull() ?: Int.MAX_VALUE }, // Сортировка цифр по значению
        { it } // Сортировка строк по алфавиту
    ))

fun GitHubRepo.getLinkedString(): String =
    "[${readmeLines?.firstOrNull()?.replaceFirst("#", "")?.trim()}]($html_url)"

fun getCurrentMoscowTimeAsString(): String {
    // 1. Получаем текущее время в Москве
    val moscowTime = ZonedDateTime.now(ZoneId.of("Europe/Moscow"))

    // 2. Форматируем в строку (день.месяц.год в часы:минуты:секунды)
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy 'в' HH:mm:ss 'MSK'")
    return moscowTime.format(formatter)
}