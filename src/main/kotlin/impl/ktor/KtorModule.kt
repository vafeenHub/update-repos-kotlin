package impl.ktor

import base.FileService
import base.Service
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.named
import org.koin.dsl.module


internal val KtorModule = module {
    factoryOf(::KtorGitHubService) {
        bind<Service>()
        named("ktor")
    }
    factoryOf(::KtorGitHubFileService) {
        bind<FileService>()
        named("Ktor")
    }
}