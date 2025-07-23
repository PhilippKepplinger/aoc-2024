package at.pkepp.puzzle21

class CommandParser {

    companion object {
        fun getCodeCommands(code: String): List<Pair<Char, Char>> {
            return (0 until code.length - 1)
                .map {
                    code[it] to code[it + 1]
                }
        }
    }
}