package data.service.client

/**
 * Объект, хранящий константы базовых URL-адресов для работы с API GitHub.
 *
 * Инкапсулирует все внешние ссылки, используемые в приложении, для удобства управления и изменения.
 */
object Links {
    /**
     * Базовый URL для GitHub REST API.
     * Используется для запросов, связанных с метаданными: списки репозиториев, коммиты и т.д.
     */
    const val BASE_URL_GITHUB_API_SERVICE = "https://api.github.com/"

    /**
     * Базовый URL для GitHub Raw Content API.
     * Используется для получения сырого (необработанного) содержимого файлов из репозиториев.
     */
    const val BASE_URL_GITHUB_FILE_SERVICE = "https://raw.githubusercontent.com/"
}