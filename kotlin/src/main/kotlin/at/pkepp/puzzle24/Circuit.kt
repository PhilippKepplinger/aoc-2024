package at.pkepp.puzzle24

import java.math.BigInteger

class Circuit(private val bits: Map<String, Bit>,
              private val operations: MutableList<Operation>) {

    fun getResult(): String {
        return getResult(bits, operations)
    }

    private fun getResult(bits: Map<String, Bit>,
                          operations: MutableList<Operation>): String {
        calculateAllBits(bits, operations)

        val bigIntResult = getDecimalResult(bits)

        return bigIntResult.toString()
    }

    private fun getDecimalResult(bits: Map<String, Bit>): BigInteger {
        return toDecimal(
            bits.filter { it.key.startsWith("z") }
        )
    }

    private fun toBinaryString(bits: Map<String, Bit>): String {
        return bits
            .asSequence()
            .toList()
            .sortedBy { it.key }
            .map { "${it.value.value}" }
            .reversed()
            .joinToString("")
    }

    private fun toDecimal(bits: Map<String, Bit>): BigInteger {
        val binaryString = toBinaryString(bits)

        val result = BigInteger(binaryString, 2)

        println("$binaryString: $result")

        return result
    }

    private fun calculateAllBits(bits: Map<String, Bit>,
                                 operations: MutableList<Operation>): Unit {
        while (bits.any { !it.value.isSet() }) {
            operations.forEach { it.perform() }
        }
    }

    fun getCorrectedResult(swapsRequired: Int): String {
        val xDecimal = toDecimal(bits.filter { it.key.startsWith("x") })
        val yDecimal = toDecimal(bits.filter { it.key.startsWith("y") })
        val zDecimal = xDecimal.add(yDecimal)
        val zBinary = zDecimal.toString(2) // expected result

        val swappedGates = mutableMapOf<String, Bit>()

        val swappedOperations = operations.toMutableList()
        val swappedBits = bits.toMap()

        return getResult(swappedBits, swappedOperations)
    }
}