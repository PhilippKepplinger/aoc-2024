package at.pkepp.puzzle24

class Bit(val name: String,
          var value: Byte? = null) {

    fun isSet(): Boolean {
        return value != null
    }

    fun isInputBit(position: Int): Boolean {
        val number = if (position < 10) "0$position" else "$position"
        return name.startsWith("x$number") || name.startsWith("y$number")
    }

    fun isOutputBit(position: Int): Boolean {
        val number = if (position < 10) "0$position" else "$position"
        return name.startsWith("z$number")
    }
}