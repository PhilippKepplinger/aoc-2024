package at.pkepp.puzzle23

class Network {
    val computers = mutableMapOf<String, Computer>()

    fun findSetsPartOne(prefix: String,
                        size: Int): Int {
        val matchingSets = mutableSetOf<List<String>>()

        val sets = computers
            .filterValues {
                it.connections.size >= size - 1 && it.name.startsWith(prefix)
            }
            .map {
                it.value
            }

        for (chiefComputer in sets) {
            print("${chiefComputer.name}: ")
            println(chiefComputer.connections.keys)
        }

        for (chiefComputer in sets) {
            for (i in 0 until chiefComputer.connections.size) {
                val currentComputer = chiefComputer.connections.values.elementAt(i)
                val matches = mutableListOf(chiefComputer.name, currentComputer.name)

                for (j in 0 until chiefComputer.connections.size) {
                    val otherComputer = chiefComputer.connections.values.elementAt(j)
                    val isCurrentComputer = currentComputer == otherComputer
                    val isChiefComputer = otherComputer == chiefComputer

                    if (!isChiefComputer && !isCurrentComputer && currentComputer.connections.containsKey(otherComputer.name)) {
                        matches.add(otherComputer.name)
                        matches.sort()

                        // found a set
                        if (matches.size == size) {
                            if (!matchingSets.contains(matches)) {
                                matchingSets.add(matches.toList())
                                println(matches)
                            }
                            matches.remove(otherComputer.name)
                        }
                    }
                }
            }
        }

        return matchingSets.size
    }

    fun findLargestSet(prefix: String): String {
        var largestSet = mutableSetOf<Computer>()

        val sets = computers
            .map {
                it.value
            }

        for (chiefComputer in sets) {
            print("${chiefComputer.name}: ")
            println(chiefComputer.connections.keys)
        }

        for (primaryComputer in sets) {
            for (i in 0 until primaryComputer.connections.size - 1) {
                val currentComputer = primaryComputer.connections.values.elementAt(i)
                val matches = mutableSetOf(primaryComputer, currentComputer)

                for (j in (i + 1) until primaryComputer.connections.size) {
                    val otherComputer = primaryComputer.connections.values.elementAt(j)
                    val isChiefComputer = otherComputer == primaryComputer

                    if (!isChiefComputer && currentComputer.isConnectedWith(otherComputer)) {
                        matches.add(otherComputer)
                    }
                }

//                if (matches.size == largestSet.size) {
//                    println("${matches.size}: ${matches}")
//                }
                if (matches.size > largestSet.size) {
                    largestSet = matches
                }

            }
        }

        return largestSet
            .map { it.name }
            .sorted()
            .joinToString(",")
    }
}