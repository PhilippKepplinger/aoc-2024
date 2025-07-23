package at.pkepp.puzzle21

import java.util.*
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class CodeDecoder(private val depth: Int) {

    private val numpadDecoder = PadDecoder(Numpad())
    private val arrowpadDecoder = PadDecoder(Arrowpad())

    fun decode(codes: List<Code>): Long {
        val start = Date().time

        val result = codes.sumOf {
            decodeSingle(it)
        }

        val end = Date().time
        val time = (end - start).toDuration(DurationUnit.MILLISECONDS)
        val duration = time.toComponents { min, sec, nanos -> "${min}:${sec}:${nanos}" }

        println("Total complexity of $result calculated in $duration")

        return result
    }

    // decodes a single code like 029A
    private fun decodeSingle(code: Code): Long {
        val numpadPaths = code.codePaths

        val arrowPadPaths = numpadPaths
            .map {
                numpadDecoder.getShortestPaths(it)
            }

        val possiblePaths = getPossibleArrowPaths(arrowPadPaths)
        println("intial paths: $possiblePaths")

        val length = decodeBroad(possiblePaths)
        val complexity = code.code * length

        println("Complexity for: ${code.code} is ${code.code} x ${length} = $complexity")
        println()

        return complexity
    }

    private fun decodeBroad(paths: List<String>): Int {
        var possiblePaths = paths

        for (n in 0 until depth) {
            val start = Date().time

            possiblePaths = possiblePaths
                .flatMap {
                    decodeSingleArrow(it)
                }

            val end = Date().time
            val time = (end - start).toDuration(DurationUnit.MILLISECONDS)
            val duration = time.inWholeSeconds

            println("depth $n took $duration sec with ${possiblePaths.size} paths")
        }

        return possiblePaths.sortedBy { it.length }[0].length
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

        val shortestPaths = paths.map {
            arrowpadDecoder.getShortestPaths(it)
        }

        return getPossibleArrowPaths(shortestPaths)
    }
}