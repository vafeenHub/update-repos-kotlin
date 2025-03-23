package base

interface Client {
    val fileService: FileService
    val fullPagedService: FullPagedService
    fun shutdown()
}