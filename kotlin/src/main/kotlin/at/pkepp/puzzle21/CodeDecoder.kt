package at.pkepp.puzzle21

import java.util.*
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class CodeDecoder(private val depth: Int) {

    private val pathElementCache = HashMap<String, List<List<String>>>()

    private val numpadDecoder = PadDecoder(Numpad())
    private val arrowpadDecoder = PadDecoder(Arrowpad())

    fun decode(codes: List<Code>): Long {
        val start = Date().time

        val result = codes.sumOf {
            decodeSingleCode(it)
        }

        val end = Date().time
        val time = (end - start).toDuration(DurationUnit.MILLISECONDS)
        val duration = time.toComponents { min, sec, nanos -> "${min}:${sec}:${nanos}" }

        println("Total complexity of $result calculated in $duration")

        return result
    }

    // decodes a single code like 029A
    private fun decodeSingleCode(code: Code): Long {
        val numpadPaths = code.codePaths

        val arrowPadPaths = numpadPaths
            .map {
                numpadDecoder.getShortestPaths(it)
            }

//        val initialPaths = getInitialArrowPaths(arrowPadPaths)
//        println("intial paths: $initialPaths")
        println("inital path elements: $arrowPadPaths")

        val length = decodeDeep(arrowPadPaths, 0)
        val complexity = code.code * length

        println("Complexity for: ${code.code} is ${code.code} x $length = $complexity")
        println()

        return complexity
    }

    // this method would explode in deeper levels
    private fun getInitialArrowPaths(arrowPaths: List<List<String>>): List<String> {
        var possiblePaths: List<String> = mutableListOf("")

        for (paths in arrowPaths) {
            possiblePaths = possiblePaths
                .flatMap { possiblePath ->
                    paths.map { possiblePath + it }
                }
        }

        return possiblePaths
    }

//    private fun decodeBroad(paths: List<String>): Int {
//        var possiblePaths = paths
//
//        for (n in 0 until depth) {
//            val start = Date().time
//
//            possiblePaths = possiblePaths
//                .flatMap {
//                    decodeSingleArrowPath(it)
//                }
//
//            val end = Date().time
//            val time = (end - start).toDuration(DurationUnit.MILLISECONDS)
//            val duration = time.inWholeSeconds
//
//            println("depth $n took $duration sec with ${possiblePaths.size} paths")
//        }
//
//        return possiblePaths.sortedBy { it.length }[0].length
//    }

    private fun decodeDeep(pathElementsList: List<List<String>>,
                           level: Int): Int {
        val resultLists = mutableListOf<List<String>>()

        for (pathElements in pathElementsList) {
            for (pathElement in pathElements) {
                val decoded = pathElementCache[pathElement] ?: decodeSingleArrowPath(pathElement)
                resultLists.addAll(decoded)
                println("$pathElement: $decoded")
            }
        }

        return 0

        //  old (working for part 1)
//        if (level == depth) {
//            return paths.sortedBy { it.length }[0].length
//        }
//
//        var min = Integer.MAX_VALUE
//
//        for (path in paths) {
//            println("level $level path $path")
//
//            val possiblePaths = decodeSingleArrowPath(path)
//            val result = decodeDeep(possiblePaths, level + 1)
//
//            if (result < min) {
//                min = result
//            }
//        }
//
//        return min
    }

    private fun decodeSingleArrowPath(path: String): List<List<String>> {
        val pathWithStart = "A$path"
        val pathElements = mutableListOf<List<String>>()

        for (i in 0 until pathWithStart.length - 1) {
            pathElements.add(
                arrowpadDecoder.getShortestPaths(pathWithStart.substring(i, i + 2))
            )
        }

        pathElementCache[path] = pathElements

        return pathElements
    }

    private fun getAllPossibleArrowPaths(arrowPaths: List<List<String>>): List<String> {
        var possiblePaths: List<String> = mutableListOf("")

        for (paths in arrowPaths) {
            possiblePaths = possiblePaths
                .flatMap { possiblePath ->
                    paths.map { possiblePath + it }
                }
        }

        return possiblePaths
    }
}