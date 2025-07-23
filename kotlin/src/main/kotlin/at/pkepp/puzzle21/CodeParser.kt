package at.pkepp.puzzle21

class CodeParser {
    companion object {
        fun parseLine(line: String): Code {
            return Code(
                CommandParser.getCodeCommands("A$line"),
                line.substring(0, line.length - 1).toLong()
            )
        }
    }
}