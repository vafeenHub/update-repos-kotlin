package impl.retrofit

import base.Client
import base.FileService
import base.FullPagedService
import okhttp3.OkHttpClient
import org.koin.core.component.KoinComponent

internal class RetrofitClient(
    override val fileService: FileService,
    override val fullPagedService: FullPagedService,
    private val okHttpClient: OkHttpClient,
) : Client, KoinComponent {
    override fun shutdown() {
        okHttpClient.dispatcher.executorService.shutdown()
        // Очищаем пул соединений
        okHttpClient.connectionPool.evictAll()
        // Закрываем кэш, если он есть
        okHttpClient.cache?.close()
    }

}