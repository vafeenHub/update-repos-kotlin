import base.Client
import base.FileService
import base.FullPagedService
import impl.ktor.KtorGitHubFileService
import impl.ktor.KtorGitHubService
import impl.retrofit.RetrofitFileService
import impl.retrofit.RetrofitService
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object Clients {
    const val BASE_URL_GITHUB_SERVICE = "https://api.github.com/"
    const val BASE_URL_GITHUB_FILE_SERVICE = "https://raw.githubusercontent.com/"
    const val NAME = "vafeenLabs"

    private val client = HttpClient(CIO) // Простой клиент без ContentNegotiation
    val ktorClient = object : Client {
        override val fileService: FileService = KtorGitHubFileService(client)
        override val fullPagedService: FullPagedService = FullPagedService(KtorGitHubService(client))
        override fun shutdown() {
            client.close()
        }
    }

    private val okHttpClient = OkHttpClient.Builder()
        .build()
    val retrofitClient = object : Client {
        override val fileService: FileService = Retrofit.Builder()
            .baseUrl(BASE_URL_GITHUB_FILE_SERVICE)
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build().create(RetrofitFileService::class.java)
        override val fullPagedService: FullPagedService = FullPagedService(
            Retrofit.Builder()
                .baseUrl(BASE_URL_GITHUB_SERVICE)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RetrofitService::class.java)
        )

        override fun shutdown() {
            okHttpClient.dispatcher.executorService.shutdown()
            // Очищаем пул соединений
            okHttpClient.connectionPool.evictAll()
            // Закрываем кэш, если он есть
            okHttpClient.cache?.close()
        }

    }
}