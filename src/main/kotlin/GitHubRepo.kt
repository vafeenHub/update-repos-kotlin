data class GitHubRepo(
    val name: String,
    val private: Boolean,
    val html_url: String, // Добавляем URL репозитория для удобства
    val readme: String?,
)