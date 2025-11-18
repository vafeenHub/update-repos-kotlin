package data

import data.service.RetrofitModule
import domain.FileContentRepository
import domain.ReadmeGettingResultLogger
import domain.ReadmeProcessor
import domain.ReposInfoRepository
import domain.ReposInfoWithReadmeRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


internal val DataModule = module {
    includes(RetrofitModule)
    factoryOf(::RetrofitFileContentRepository) { bind<FileContentRepository>() }
    factoryOf(::RetrofitReposInfoRepository) { bind<ReposInfoRepository>() }
    factoryOf(::RepoInfoWithReadmeRepositoryImpl) { bind<ReposInfoWithReadmeRepository>() }
    singleOf(::ConsoleReadmeGettingResultLogger) { bind<ReadmeGettingResultLogger>() }
    factoryOf(::ReadmeProcessorImpl) { bind<ReadmeProcessor>() }
}