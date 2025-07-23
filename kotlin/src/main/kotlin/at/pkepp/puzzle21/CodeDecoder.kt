package at.pkepp.puzzle21

class CodeDecoder(private val depth: Int) {

    private val numpadDecoder = PadDecoder(Numpad())
    private val arrowpadDecoder = PadDecoder(Arrowpad())

    fun decode(codes: List<Code>): Long {
        return codes.sumOf {
            decodeSingle(it)
        }
    }

    // decodes a single code like 029A
    private fun decodeSingle(code: Code): Long {
        val numpadPaths = code.codePaths
        println("code paths: $numpadPaths")

        val arrowPadPaths = numpadPaths
            .map {
                numpadDecoder.getShortestPaths(it.first, it.second)
            }
        println("arrow pad paths: $arrowPadPaths")

        var possiblePaths = getPossibleArrowPaths(arrowPadPaths)
        println("possible paths: $possiblePaths")

        for (n in 0 until depth) {
            possiblePaths = possiblePaths
                .flatMap {
                    decodeSingleArrow(it)
                }

            println("paths: ${possiblePaths.size}")
        }


        val shortest = possiblePaths.sortedBy { it.length }[0]
        val complexity = code.code * shortest.length

        println("Complexity for: ${code.code} is ${code.code} x ${shortest.length} = $complexity")
        println()

        return complexity
    }

    private fun getPossibleArrowPaths(arrowPaths: List<List<String>>): List<String> {
        var possiblePaths: List<String> = mutableListOf("")

        for (paths in arrowPaths) {
            possiblePaths = possiblePaths.flatMap { possiblePath ->
                paths.map { possiblePath + it }
            }
        }

        return possiblePaths
    }

    private fun decodeSingleArrow(path: String): List<String> {
        val paths = CommandParser.getCodeCommands("A$path")
        val shortestPaths = paths.map { arrowpadDecoder.getShortestPaths(it.first, it.second) }
        return getPossibleArrowPaths(shortestPaths)
    }
}