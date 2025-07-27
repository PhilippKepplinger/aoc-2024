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

    fun findLargestSet(): String {
        val sets = computers
            .filter {
                it.key.startsWith("t")
            }
            .map {
                it.value
            }

        for (chiefComputer in sets) {
            print("${chiefComputer.name}: ")
            println(chiefComputer.connections.keys)
        }

        val mostConnectionPerComputer = mutableListOf<List<Computer>>()

        for (primaryComputer in sets) {
            val matchMap = mutableListOf<Pair<Int, Computer>>()

            for (i in 0 until primaryComputer.connections.size) {
                val currentComputer = primaryComputer.connections.values.elementAt(i)
                val matches = mutableSetOf(primaryComputer)

                for (j in 0 until primaryComputer.connections.size) {
                    val otherComputer = primaryComputer.connections.values.elementAt(j)
                    val isChiefComputer = otherComputer == primaryComputer

                    if (!isChiefComputer && currentComputer.isConnectedWith(otherComputer)) {
                        matches.add(otherComputer)
                    }
                }

                matchMap.add(matches.size to currentComputer)
            }

            val mostConnections = matchMap
                .groupBy { it.first }
                .values
                .sortedBy { -it.size }
                .get(0)
                .map { it.second }
                .toMutableList()

            mostConnections.add(primaryComputer)
            mostConnectionPerComputer.add(mostConnections)
        }

        val sortedRes = mostConnectionPerComputer
            .sortedBy { -it.size }
            .get(0)

        return sortedRes
            .map { it.name }
            .sorted()
            .joinToString(",")
    }
}