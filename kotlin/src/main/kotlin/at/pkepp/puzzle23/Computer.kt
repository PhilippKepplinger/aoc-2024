package at.pkepp.puzzle23

class Computer(val name: String) {
    val connections = mutableMapOf<String, Computer>()

    fun isConnectedWith(otherComputer: Computer): Boolean {
        return connections.containsKey(otherComputer.name)
    }
}