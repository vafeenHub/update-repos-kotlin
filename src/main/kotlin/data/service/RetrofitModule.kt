package data.service


import data.RepoInfoWithReadmeRepositoryImpl
import data.RetrofitFileContentRepository
import data.RetrofitReposInfoRepository
import data.service.Qualifiers.GitHubApi
import data.service.Qualifiers.GitHubRaw
import data.service.client.Links
import data.service.client.RetrofitClient
import data.service.client.base.Client
import domain.FileContentRepository
import domain.ReposInfoRepository
import domain.ReposInfoWithReadmeRepository
import okhttp3.OkHttpClient
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

val AppUpdateReposModule = module {
    single<OkHttpClient> { OkHttpClient.Builder().build() }
    singleOf(::RetrofitClient).bind<Client>()
    single<Retrofit>(GitHubApi) {
        Retrofit.Builder()
            .baseUrl(Links.BASE_URL_GITHUB_API_SERVICE)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<Retrofit>(GitHubRaw) {
        Retrofit.Builder()
            .baseUrl(Links.BASE_URL_GITHUB_FILE_SERVICE)
            .client(get())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<GitHubApiService> { get<Retrofit>(GitHubApi).create(GitHubApiService::class.java) }
    single<GitHubRawContentService> { get<Retrofit>(GitHubRaw).create(GitHubRawContentService::class.java) }

    factory<ReposInfoRepository> { RetrofitReposInfoRepository(get()) }
    factory<FileContentRepository> { RetrofitFileContentRepository(get()) }
    factory<ReposInfoWithReadmeRepository> { RepoInfoWithReadmeRepositoryImpl(get(), get()) }
}
