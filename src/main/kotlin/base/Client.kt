package base

interface Client {
    val fileService: FileService
    val service: Service
    fun shutdown()
}