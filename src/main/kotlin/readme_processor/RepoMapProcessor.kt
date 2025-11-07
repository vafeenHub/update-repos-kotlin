package readme_processor

import java.io.File

/**
 * Определяет контракт для процессоров, которые генерируют и записывают содержимое README.
 *
 * Этот интерфейс абстрагирует логику вывода, позволяя легко переключаться
 * между различными целями, такими как файл или консоль.
 */
interface RepoMapProcessor {
    /**
     * Добавляет текстовую строку к текущему выводу.
     * @param text Текст для добавления.
     */
    fun append(text: String)

    /**
     * Добавляет разрыв строки (или несколько) к текущему выводу.
     */
    fun newLine()

    /**
     * Очищает целевой вывод, подготавливая его к новой записи.
     */
    fun clear()

    companion object {
        val CONSOLE = ConsoleRepoMapProcessor
        val FILE = FileRepoMapProcessor
    }
}

/**
 * Реализация [RepoMapProcessor], которая выводит сгенерированное содержимое в консоль.
 *
 * Полезна для отладки и быстрой проверки результата без изменения файлов.
 */
object ConsoleRepoMapProcessor : RepoMapProcessor {
    override fun append(text: String) {
        print(text)
    }

    override fun newLine() {
        print("\n\n")
    }

    override fun clear() {}
}

/**
 * Реализация [RepoMapProcessor], которая записывает сгенерированное содержимое в файл.
 *
 * По умолчанию запись ведется в файл `README2.md` в корневом каталоге проекта.
 */
object FileRepoMapProcessor : RepoMapProcessor {
    private val file = File("profile/README.md")

    init {
        file.createNewFile()
    }

    override fun append(text: String) {
        file.appendText(text)
    }

    override fun newLine() {
        append("\n\n")
    }

    override fun clear() {
        file.writeText("")
    }
}