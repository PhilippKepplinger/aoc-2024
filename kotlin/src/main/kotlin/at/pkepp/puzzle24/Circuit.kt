package at.pkepp.puzzle24

import java.math.BigInteger

class Circuit(private val bits: Map<String, Bit>,
              private val operations: MutableList<Operation>) {

    fun getResult(): String {
        calculateAllBits()

        val outputBits = bits
            .asSequence()
            .filter { it.key.startsWith("z") }
            .toList()
            .sortedBy { it.key }
            .map { "${it.value.value}" }
            .reversed()

        val bitString = outputBits
            .joinToString("")

        val result = BigInteger(bitString, 2)

        println("${bitString}: ${result}")

        return result.toString()
    }

    fun calculateAllBits(): Unit {
        while (bits.any { !it.value.isSet() }) {
            operations.forEach { it.perform() }
        }
    }

    fun getResultPartTwo(): String {
        return ""
    }

}