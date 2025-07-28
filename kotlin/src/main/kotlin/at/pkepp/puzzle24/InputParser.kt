package at.pkepp.puzzle24

import java.io.File

class InputParser(private val puzzle: String,
                  private val debug: Boolean = false) {

    fun parseFile(): Circuit {
        val file = if (debug) "example.txt" else "input.txt"

        val url =
            this::class.java.getResource("/puzzle$puzzle/$file")
                ?: throw IllegalArgumentException("Input file $puzzle not found.")

        val lines = File(url.toURI())
            .useLines {
                it.toList()
            }

        val inputSeparator = lines.indexOf("")
        val inputBits = lines.subList(0, inputSeparator)
        val bitConnections = lines.subList(inputSeparator + 1, lines.size)

        return parseCircuit(
            paresInputBits(inputBits),
            bitConnections
        )
    }

    private fun paresInputBits(inputBits: List<String>): MutableMap<String, Bit> {
        val result = mutableMapOf<String, Bit>()

        inputBits.forEach {
            val (name, value) = it.split(": ")
            val bit = Bit(name, value.toInt().toByte())
            result[name] = bit
        }

        return result
    }

    private fun parseCircuit(bits: MutableMap<String, Bit>,
                             connections: List<String>): Circuit {
        val operations = mutableListOf<Operation>()

        connections.forEach {
            val elements = it.split(" ")
            val bitA = bits.getOrDefault(elements[0], Bit(elements[0]))
            val operator = elements[1]
            val bitB = bits.getOrDefault(elements[2], Bit(elements[2]))
            val bitOutput = bits.getOrDefault(elements[4], Bit(elements[4]))

            bits.putIfAbsent(bitA.name, bitA)
            bits.putIfAbsent(bitB.name, bitB)
            bits.putIfAbsent(bitOutput.name, bitOutput)

            operations.add(
                Operation(bitA, operator, bitB, bitOutput)
            )
        }

        return Circuit(bits, operations)
    }
}