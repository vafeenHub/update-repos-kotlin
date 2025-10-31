package data.service

import org.koin.core.qualifier.named

/**
 * Объект, содержащий квалификаторы для зависимостей Koin.
 *
 * Квалификаторы используются для различения зависимостей одного и того же типа.
 * В данном случае, они различают Retrofit-клиенты и сервисы для разных GitHub API.
 */
object Qualifiers {
    /**
     * Квалификатор для зависимостей, связанных с GitHub REST API.
     * Используется для получения метаданных репозиториев, списков коммитов и т.д.
     */
    val GitHubApi = named("GitHubApi")

    /**
     * Квалификатор для зависимостей, связанных с GitHub Raw Content API.
     * Используется для получения сырого содержимого файлов, например, README.md.
     */
    val GitHubRaw = named("GitHubRaw")
}