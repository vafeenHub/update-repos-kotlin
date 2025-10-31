package data.service.client

import data.service.client.base.Client
import okhttp3.OkHttpClient

/**
 * Реализация [Client], обертывающая [OkHttpClient] для управления его жизненным циклом.
 *
 * Отвечает за корректное освобождение ресурсов, таких как пулы потоков и соединений,
 * которые использует OkHttpClient.
 *
 * @property okHttpClient Экземпляр [OkHttpClient], жизненным циклом которого управляет данный клиент.
 */
internal class RetrofitClient(
    val okHttpClient: OkHttpClient,
) : Client {

    /**
     * Инициирует корректное завершение работы [OkHttpClient].
     *
     * Этот метод последовательно выполняет следующие действия:
     * 1. Останавливает сервис исполнителя (thread pool), предотвращая прием новых задач.
     * 2. Вытесняет все соединения из пула соединений.
     * 3. Закрывает кэш, если он был сконфигурирован.
     *
     * Это необходимо для предотвращения утечек ресурсов и зависания приложения после завершения работы.
     */
    override fun closeConnection() {
        okHttpClient.dispatcher.executorService.shutdown()
        okHttpClient.connectionPool.evictAll()
        okHttpClient.cache?.close()
    }
}