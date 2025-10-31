package domain.models

/**
 * Представляет репозиторий GitHub.
 *
 * Содержит основную информацию о репозитории, а также может включать
 * обработанное содержимое его README-файла.
 *
 * @property name Название репозитория.
 * @property private Флаг, указывающий, является ли репозиторий приватным.
 * @property html_url URL-адрес репозитория на GitHub.
 * @property readmeLines Содержимое файла README.md в виде списка непустых строк, или null, если файл не загружен.
 * @property default_branch Название ветки по умолчанию (например, "main" или "master").
 */
data class GitHubRepo(
    val name: String,
    val private: Boolean,
    val html_url: String,
    val readmeLines: List<String>?,
    val default_branch: String,
)

/**
 * Извлекает номера семестров из названия репозитория.
 *
 * Свойство анализирует имя репозитория, и если оно содержит ключевое слово "semester",
 * то извлекает из него номера семестров, разделенные дефисом.
 * Например, из "project_1-2semester" будут извлечены "1" и "2".
 *
 * @return Список строк с номерами семестров или null, если название не соответствует формату.
 */
val GitHubRepo.semesters: List<String>?
    get() = if (name.contains("semester"))
        name.substringAfter("_").substringBefore("semester").split("-").filter { it.isNotBlank() }
    else null
