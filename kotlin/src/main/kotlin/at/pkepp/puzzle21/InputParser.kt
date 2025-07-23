package at.pkepp.puzzle21

import java.io.File

class InputParser(private val puzzle: String,
                  private val debug: Boolean = false) {

    fun parseFile(): List<Code> {
        val file = if (debug) "example.txt" else "input.txt"

        val url =
            this::class.java.getResource("/puzzle$puzzle/$file")
                ?: throw IllegalArgumentException("Input file $puzzle not found.")

        return File(url.toURI())
            .useLines {
                it.toList()
            }
            .map {
                parseLine(it)
            }
    }

    fun parseLine(line: String): Code {
        return Code(
            CommandParser.getCodeCommands("A$line"),
            line.substring(0, line.length - 1).toLong()
        )
    }
}