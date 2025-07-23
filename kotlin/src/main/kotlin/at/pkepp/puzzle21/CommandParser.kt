package at.pkepp.puzzle21

class CommandParser {

    companion object {
        fun getCodeCommands(code: String): List<String> {
            return (0 until code.length - 1)
                .map {
                    code.substring(it, it + 2)
                }
        }
    }
}