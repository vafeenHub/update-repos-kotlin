package data.service

import org.koin.core.qualifier.named

object Qualifiers {
    val GitHubApi = named("GitHubApi")
    val GitHubRaw = named("GitHubRaw")
}