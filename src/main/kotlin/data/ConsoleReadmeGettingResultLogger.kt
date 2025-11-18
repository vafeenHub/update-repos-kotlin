package data

import domain.ReadmeGettingResultLogger

internal class ConsoleReadmeGettingResultLogger : ReadmeGettingResultLogger {
    override fun addRow(name: String, result: Boolean) {
        println("$name: $result")
    }

}