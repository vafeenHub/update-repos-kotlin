package domain.models

data class GitHubRepo(
    val name: String,
    val private: Boolean,
    val html_url: String,
    val readmeLines: List<String>?,
    val default_branch: String,
)

val GitHubRepo.semesters: List<String>?
    get() = if (name.contains("semester"))
        name.substringAfter("_").substringBefore("semester").split("-").filter { it.isNotBlank() }
    else null
