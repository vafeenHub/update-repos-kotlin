package readme_processor

import java.io.File

interface RepoMapProcessor {
    fun append(text: String)
    fun newLine()
    fun clear()
}

object ConsoleRepoMapProcessor : RepoMapProcessor {
    override fun append(text: String) {
        print(text)
    }

    override fun newLine() {
        print("\n\n")
    }

    override fun clear() {}
}

object FileRepoMapProcessor : RepoMapProcessor {
    private val file = File(
        "README2.md"
//        "profile/README.md"
    )

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