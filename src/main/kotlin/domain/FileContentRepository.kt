package domain

/**
 * Репозиторий для получения сырого (raw) содержимого файлов из репозиториев.
 *
 * Определяет контракт для загрузки текстового содержимого файла,
 * например, README.md, из указанного репозитория.
 */
interface FileContentRepository {
    /**
     * Получает сырое текстовое содержимое файла из указанного репозитория.
     *
     * @param accountName Имя учетной записи пользователя GitHub.
     * @param repoName Название репозитория.
     * @param defaultBranch Название ветки по умолчанию (например, "main" или "master").
     * @param fileName Имя файла для загрузки (например, "README.md").
     * @return Содержимое файла в виде [String] или null, если файл не найден или произошла ошибка.
     */
    suspend fun getRawContent(
        accountName: String,
        repoName: String,
        defaultBranch: String,
        fileName: String
    ): String?
}