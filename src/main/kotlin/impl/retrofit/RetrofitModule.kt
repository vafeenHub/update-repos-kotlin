package impl.retrofit


import RepoInfoWithReadmeRepository
import base.Client
import base.FileService
import base.FullPagedService
import base.Service
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

internal const val RetrofitName = "Retrofit"

internal val RetrofitModule = module {
    single<OkHttpClient> { OkHttpClient.Builder().build() }

    factory<FileService>(named(RetrofitName)) {
        Retrofit.Builder()
            .baseUrl(Links.BASE_URL_GITHUB_FILE_SERVICE)
            .client(get())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build().create(RetrofitFileService::class.java)
    }

    factory<Service>(named(RetrofitName)) {
        Retrofit.Builder()
            .baseUrl(Links.BASE_URL_GITHUB_SERVICE)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RetrofitService::class.java)
    }
    
    factory<FullPagedService>(named(RetrofitName)) {
        FullPagedService(get(named(RetrofitName)))
    }

    factory<Client>(named(RetrofitName)) {
        RetrofitClient(
            fileService = get(named(RetrofitName)),
            fullPagedService = get(named(RetrofitName)),
            okHttpClient = get()
        )
    }

    factory { RepoInfoWithReadmeRepository(get(named(RetrofitName))) }
}
