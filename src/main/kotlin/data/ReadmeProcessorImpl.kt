package data

import domain.ReadmeGettingResultLogger
import domain.ReadmeProcessor
import domain.RepoMap
import domain.models.GitHubRepo
import readme_processor.RepoMapProcessor
import java.io.File
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


internal class ReadmeProcessorImpl(
    private val logger: ReadmeGettingResultLogger
) : ReadmeProcessor {

    /**
     * Обрабатывает и генерирует содержимое файла README на основе карты репозиториев.
     *
     * @param repoMap Карта репозиториев, сгруппированных по ключам (например, семестрам).
     * @param repoMapProcessor Процессор, отвечающий за запись сгенерированного содержимого.
     */
    override fun processReadme(
        repoMap: RepoMap,
        repoMapProcessor: RepoMapProcessor,
    ) {
        repoMapProcessor.apply {
            clear()
            append(getContentFromTemplateReadme())
            newLine()
            append(">Updated ${getCurrentMoscowTimeAsString()}")
            newLine()
            repoMap.keys.sortedAsSemesters().forEach { key ->
//            <details>
//<summary>Semester: 2</summary>
//
//- [Информатика и программирование 2 семестр: Курсовая](https://github.com/vafeenHub/computer-science-and-programming_2-semester)
//- [Пакеты прикладных программ 2 семестр](https://github.com/vafeenHub/application-software-packages_2-semester)
//
//</details>
                append(
                    "<details>\n" +
                            "<summary>${if (key != "others") "Semester: " else ""}$key</summary>"
                )
                newLine()
                repoMap[key]?.sortedBy {
                    it.readmeLines?.firstOrNull()
                }?.forEach { repo ->
                    append(repo.getLinkedString())
                    newLine()
                }
                append("</details>")
                newLine()
            }
        }
    }

    /**
     * Сортирует набор строк, интерпретируя их как номера семестров.
     *
     * Сортировка происходит по следующим правилам:
     * 1. Строки, состоящие только из цифр, идут первыми.
     * 2. Числовые строки сортируются по своему числовому значению.
     * 3. Остальные строки (нечисловые) идут после числовых и сортируются по алфавиту.
     *
     * @return Отсортированный список строк.
     */
    private fun Set<String>.sortedAsSemesters(): List<String> = this.sortedWith(
        compareBy(
            { !it.all { char -> char.isDigit() } }, // Сначала строки с цифрами, затем с буквами
            { it.toIntOrNull() ?: Int.MAX_VALUE }, // Сортировка цифр по значению
            { it } // Сортировка строк по алфавиту
        ))

    /**
     * Создает строку в формате Markdown-ссылки для репозитория.
     *
     * В качестве текста ссылки используется первая строка из `readmeLines`.
     * В качестве URL используется `html_url` репозитория.
     *
     * @return Строка в формате "[Текст ссылки](URL)".
     */
    private fun GitHubRepo.getLinkedString(): String =
        "[${readmeLines?.firstOrNull()?.replaceFirst("#", "")?.trim()}]($html_url)"

    /**
     * Получает и форматирует текущее время по московскому времени в виде строки.
     *
     * @return Строка с датой и временем в формате "dd.MM.yyyy 'в' HH:mm:ss 'MSK'".
     */
    private fun getCurrentMoscowTimeAsString(): String {
        // 1. Получаем текущее время в Москве
        val moscowTime = ZonedDateTime.now(ZoneId.of("Europe/Moscow"))

        // 2. Форматируем в строку (день.месяц.год в часы:минуты:секунды)
        val formatter = DateTimeFormatter.ofPattern("'**'dd.MM.yyyy'**' 'в' '**'HH:mm:ss'**' 'MSK'")
        return moscowTime.format(formatter)
    }

    /**
     * Считывает и возвращает содержимое файла "README.md" из корневого каталога проекта.
     *
     * @return Содержимое файла в виде строки.
     */
    private fun getContentFromTemplateReadme(): String = File("README.md").readText()
}