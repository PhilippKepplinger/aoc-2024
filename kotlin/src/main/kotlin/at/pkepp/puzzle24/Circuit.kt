package at.pkepp.puzzle24

import java.math.BigInteger

class Circuit(private val bits: Map<String, Bit>,
              private val operations: MutableList<Operation>) {

    /**
     * Calculates all outputs of all gates until each bit has a value
     */
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

    /**
     * for each order of the binary input, validate if the current order has a valid full-adder installed.
     * If not, try to fix the adder by checking various error conditions for which a swap can be done with 100% certainty
     */
    fun getCorrectedResult(): String {
        val swappedOperations = operations.toMutableList()
        val swappedBits = bits.toMap()

        fixAdderCircuit(swappedBits, swappedOperations)
        return getResult(swappedBits, swappedOperations)
    }

    private fun fixAdderCircuit(bits: Map<String, Bit>,
                                operations: MutableList<Operation>): Unit {
        val swappedOutputBits = mutableListOf<String>()

        val nInputs = bits.keys.count { it.startsWith("x") }
        var cBit: Bit? = null

        for (n in 0 until nInputs) {
            var valid: Boolean

            if (n == 0) {
                // is valid because I checked from input data that it is a valid half adder xD
                val xorGate = operations.firstOrNull { it.isXOR() && it.a.isInputBit(0) && it.b.isInputBit(0) }
                val andGate = operations.firstOrNull { it.isAND() && it.a.isInputBit(0) && it.b.isInputBit(0) }
                val z1 = xorGate?.output
                cBit = andGate?.output // could still be swapped because cBit could point to the wrong gate
                valid = z1 != null && z1.isOutputBit(0) && cBit != null
            } else {
                // test if the current order represents a full adder
                println("${cBit?.name} (cBIT)")

                // x and y must always go to one XOR
                val inputXOR = operations.first { it.isXOR() && it.a.isInputBit(n) && it.b.isInputBit(n) }

                // the carry over bit (cBit) and the output from the inputXOR must be the output on the current order
                var outputXOR = operations.firstOrNull { it.isXOR() && it.hasBits(inputXOR.output, cBit) }

                // no outputXOR found? let's check why
                if (outputXOR == null) {

                    // check if a XOR can be found with the cBit as input, which is an output bit (z Bit)
                    outputXOR =
                        operations.firstOrNull { it.isXOR() && it.output.isOutputBit(n) && (it.a == cBit || it.b == cBit) }
                    if (outputXOR != null) {
                        // outputXOR is valid
                        // inputXOR output is invalid and needs to match the second bit of the outputXOR
                        val requiredOutputBit = if (outputXOR.a == cBit) outputXOR.b else outputXOR.a
                        val swapOperation = operations.first { it.output == requiredOutputBit }
                        val outputPair = swapOutputs(inputXOR, swapOperation)
                        swappedOutputBits.add(outputPair.first)
                        swappedOutputBits.add(outputPair.second)
                    }

                    // luckily, no more checks needed :)
                }

                // x and y must always go to one AND
                val inputAND = operations.first { it.isAND() && it.a.isInputBit(n) && it.b.isInputBit(n) }

                // output XOR found but is not an output bit, find output at position and swap. This is always a valid swap.
                if (outputXOR != null && !outputXOR.output.isOutputBit(n)) {
                    val validOutputOperation = operations.first { it.output.isOutputBit(n) }
                    val outputPair = swapOutputs(outputXOR, validOutputOperation)
                    swappedOutputBits.add(outputPair.first)
                    swappedOutputBits.add(outputPair.second)
                }

                val cAND = operations.firstOrNull { it.isAND() && it.hasBits(inputXOR.output, cBit) }
                val cOR = operations.firstOrNull { it.isOR() && it.hasBits(inputAND.output, cAND?.output) }
                cBit = cOR?.output

                // there could be problems as well, but the puzzle worked already, so not all cases are covered

                val isLastOutputBitValid = n < nInputs - 1 || (cBit != null && cBit.isOutputBit(n + 1))
                valid = cBit != null
                        && outputXOR?.output != null
                        && outputXOR.output.isOutputBit(n)
                        && isLastOutputBitValid

                println("${inputXOR.a.name} XOR ${inputXOR.b.name} = ${inputXOR.output.name}")
                println("              ${outputXOR?.a?.name} XOR ${outputXOR?.b?.name} = ${outputXOR?.output?.name}")
                println("              ${cAND?.a?.name} AND ${cAND?.b?.name} = ${cAND?.output?.name}")
                println("${inputAND.a.name} AND ${inputAND.b.name} = ${inputAND.output.name}")
                println("                            ${cOR?.a?.name} OR ${cOR?.b?.name} = ${cOR?.output?.name}")
            }
            println("$n: $valid")
            println("==============================================")
        }

        // print swapped output bits
        println(swappedOutputBits.sorted().joinToString(","))
    }

    private fun swapOutputs(a: Operation,
                            b: Operation): Pair<String, String> {
        val temp = a.output
        a.output = b.output
        b.output = temp

        return a.output.name to b.output.name
    }
}