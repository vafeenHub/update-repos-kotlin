import data.DataModule
import data.service.client.base.Client
import domain.ReadmeProcessor
import domain.ReposInfoWithReadmeRepository
import domain.add
import domain.models.semesters
import domain.repoMapOf
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.filter
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin
import readme_processor.RepoMapProcessor


/**
 * Главная функция приложения, отвечающая за обновление README файла с проектами.
 *
 * Приложение выполняет следующие шаги:
 * 1. Разбирает аргументы командной строки для получения имени аккаунта и других опций.
 * 2. Инициализирует Koin для внедрения зависимостей.
 * 3. Получает список репозиториев для указанного аккаунта, фильтрует их и загружает содержимое README.
 * 4. Группирует репозитории по семестрам на основе их названия.
 * 5. Генерирует новый README файл на основе полученных данных.
 * 6. Корректно завершает работу сетевого клиента.
 *
 * @param args Аргументы командной строки.
 *  - `-accountName` или `-ac`: (обязательный) Имя аккаунта GitHub, для которого нужно обновить README.
 *  - `-fullreadme` или `-fr`: (опциональный) Флаг для генерации полной версии README.
 */
suspend fun main(args: Array<String>) = coroutineScope {

    var accountName: String? = null
    var i = 0
    while (i < args.size) {
        when (args[i]) {
            "-accountName", "-ac" -> {
                if (i + 1 < args.size) {
                    accountName = args[i + 1]
                    i++
                }
            }
        }
        i++
    }

    if (accountName == null) {
        println("Please provide an account name using -accountName <name> or -ac <name>")
        return@coroutineScope
    }
    startKoin {
        modules(DataModule)
    }
    val koin = getKoin()
    val reposInfoWithReadmeRepository = koin.get<ReposInfoWithReadmeRepository>()

    val repoMap = repoMapOf()

    reposInfoWithReadmeRepository.getInfo(
        accountName = accountName,
        filterPredicate = { it.name != ".github" && !it.private })
        .filter { it.readmeLines != null }
        .collect { repo ->
            val semesters = repo.semesters
            if (semesters == null) {
                repoMap.add("others", repo)
            } else {
                semesters.forEach { repoMap.add(it, repo) }
            }
        }

    getKoin().get<ReadmeProcessor>()
        .processReadme(
            repoMap = repoMap,
            repoMapProcessor = RepoMapProcessor.FILE,
        )
    val client = koin.get<Client>()
    client.closeConnection()
}
