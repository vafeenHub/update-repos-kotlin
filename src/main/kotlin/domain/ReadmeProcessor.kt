package domain

import domain.models.GitHubRepo
import readme_processor.RepoMapProcessor

interface ReadmeProcessor {
    /**
     * Обрабатывает и генерирует содержимое файла README на основе карты репозиториев.
     *
     * @param repoMap Карта репозиториев, сгруппированных по ключам (например, семестрам).
     * @param repoMapProcessor Процессор, отвечающий за запись сгенерированного содержимого.
     */
    fun processReadme(
        repoMap: RepoMap,
        repoMapProcessor: RepoMapProcessor,
    )
}

/**
 * Псевдоним для словаря, который сопоставляет ключ (например, номер семестра) со списком репозиториев.
 */
typealias RepoMap = MutableMap<String, MutableList<GitHubRepo>>

/**
 * Создает и возвращает пустой экземпляр [RepoMap].
 */
fun repoMapOf(): RepoMap = mutableMapOf()

/**
 * Добавляет репозиторий [value] в список по ключу [key].
 *
 * Если для указанного ключа еще не существует списка, он будет создан.
 *
 * @param key Ключ для группировки (например, номер семестра).
 * @param value Репозиторий [GitHubRepo] для добавления.
 */
fun RepoMap.add(key: String, value: GitHubRepo) {
    if (this[key] == null)
        this[key] = mutableListOf()
    this[key]?.add(value)
}