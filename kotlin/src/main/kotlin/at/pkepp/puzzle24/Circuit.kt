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
        calculateAllBits(swappedBits, swappedOperations)

        val invalidOperations = getInvalidOperations(swappedBits, swappedOperations)

        return getResult(swappedBits, swappedOperations)
    }

    private fun getInvalidOperations(bits: Map<String, Bit>,
                                     operations: MutableList<Operation>): MutableSet<Operation> {
        val swappedBits = mutableListOf<String>()
        val invalidOperations = mutableSetOf<Operation>()

        val nInputs = bits.keys.count { it.startsWith("x") }
        val validations = BooleanArray(nInputs)

        var cBit: Bit? = null
        var lastCBitOR: Operation? = null

        for (n in 0 until nInputs) {
            if (n == 0) {
                // is valid because I checked from input data that it is a valid half adder xD
                val xorGate = operations.firstOrNull { it.isXOR() && it.a.isInputBit(0) && it.b.isInputBit(0) }
                val andGate = operations.firstOrNull { it.isAND() && it.a.isInputBit(0) && it.b.isInputBit(0) }
                val z1 = xorGate?.output
                cBit = andGate?.output // could still be swapped because cBit could point to the wrong gate
                validations[n] = z1 != null && z1.isOutputBit(0) && cBit != null
            } else {
                // test if the current level is a valid full adder
                println("${cBit?.name} (cBIT)")

                // x and y must always go to an XOR and to an AND gate
                val inputXOR = operations.first { it.isXOR() && it.a.isInputBit(n) && it.b.isInputBit(n) }
                var outputXOR = operations.firstOrNull { it.isXOR() && it.hasBits(inputXOR.output, cBit) }

                if (outputXOR == null) {
                    outputXOR = operations.firstOrNull { it.isXOR() && it.a == cBit || it.b == cBit }
                    if (outputXOR != null && outputXOR.output.isOutputBit(n)) {
                        // outputXOR is valid
                        // inputXOR output is invalid and needs to match the second bit of the outputXOR
                        val requiredOutputBit = if (outputXOR.a == cBit) outputXOR.b else outputXOR.a
                        val swapOperation = operations.first { it.output == requiredOutputBit }
                        val outputPair = swapOutputs(inputXOR, swapOperation)
                        swappedBits.add(outputPair.first)
                        swappedBits.add(outputPair.second)
                    }
                }

                val inputAND = operations.first { it.isAND() && it.a.isInputBit(n) && it.b.isInputBit(n) }

                // output XOR is not an output bit, find output at position and swap. This should be a valid swap always
                if (outputXOR != null && !outputXOR.output.isOutputBit(n)) {
                    val validOutputOperation = operations.first { it.output.isOutputBit(n) }
                    val outputPair = swapOutputs(outputXOR, validOutputOperation)
                    swappedBits.add(outputPair.first)
                    swappedBits.add(outputPair.second)
                }

                // dck XOR ctg -> z15
                //

                val cAND = operations.firstOrNull { it.isAND() && it.hasBits(inputXOR?.output, cBit) }
                val cOR = operations.firstOrNull { it.isOR() && it.hasBits(inputAND?.output, cAND?.output) }
                cBit = cOR?.output
                lastCBitOR = cOR // if cBit is invalid, this operation is also invalid
                val isLastOutputBitValid = n < nInputs - 1 || (cBit != null && cBit.isOutputBit(n))
                validations[n] = cBit != null
                        && outputXOR?.output != null
                        && outputXOR.output.isOutputBit(n)
                        && isLastOutputBitValid

                println("${inputXOR?.a?.name} XOR ${inputXOR?.b?.name} = ${inputXOR?.output?.name}")
                println("              ${outputXOR?.a?.name} XOR ${outputXOR?.b?.name} = ${outputXOR?.output?.name}")
                println("              ${cAND?.a?.name} AND ${cAND?.b?.name} = ${cAND?.output?.name}")
                println("${inputAND?.a?.name} AND ${inputAND?.b?.name} = ${inputAND?.output?.name}")
                println("                            ${cOR?.a?.name} OR ${cOR?.b?.name} = ${cOR?.output?.name}")
                println("$n: ${validations[n]}")
                println("==============================================")
            }
        }

        println(swappedBits.sorted().joinToString(","))

        return invalidOperations
    }

    private fun swapOutputs(a: Operation,
                            b: Operation): Pair<String, String> {
        val temp = a.output
        a.output = b.output
        b.output = temp

        return a.output.name to b.output.name
    }
}