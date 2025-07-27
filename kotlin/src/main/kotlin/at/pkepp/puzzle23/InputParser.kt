package at.pkepp.puzzle23

import java.io.File

class InputParser(private val puzzle: String,
                  private val debug: Boolean = false) {

    fun parseFile(): Network {
        val file = if (debug) "example.txt" else "input.txt"

        val url =
            this::class.java.getResource("/puzzle$puzzle/$file")
                ?: throw IllegalArgumentException("Input file $puzzle not found.")

        val network = Network()

        File(url.toURI())
            .useLines {
                it.toList()
            }
            .map {
                parseLine(network, it)
            }


        network.computers.forEach {
            it.value.connections = it.value.connections.toSortedMap()
        }

        return network
    }

    private fun parseLine(network: Network,
                          line: String): Unit {
        val (leftId, rightId) = line.split("-")
        val leftComputer = network.computers.getOrDefault(leftId, Computer(leftId))
        val rightComputer = network.computers.getOrDefault(rightId, Computer(rightId))

        leftComputer.connections[rightId] = rightComputer
        rightComputer.connections[leftId] = leftComputer

        network.computers.putIfAbsent(leftId, leftComputer)
        network.computers.putIfAbsent(rightId, rightComputer)
    }
}