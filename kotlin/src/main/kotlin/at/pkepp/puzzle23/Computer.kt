package at.pkepp.puzzle23

class Computer(val name: String) {
    var connections = mutableMapOf<String, Computer>()

    fun isConnectedWith(otherComputer: Computer): Boolean {
        return connections.containsKey(otherComputer.name)
    }
}