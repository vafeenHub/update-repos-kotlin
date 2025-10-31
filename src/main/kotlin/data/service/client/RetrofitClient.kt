package data.service.client

import data.service.client.base.Client
import okhttp3.OkHttpClient

internal class RetrofitClient(
    val okHttpClient: OkHttpClient,
) : Client {
    override fun closeConnection() {
        okHttpClient.dispatcher.executorService.shutdown()
        // Очищаем пул соединений
        okHttpClient.connectionPool.evictAll()
        // Закрываем кэш, если он есть
        okHttpClient.cache?.close()
    }
}