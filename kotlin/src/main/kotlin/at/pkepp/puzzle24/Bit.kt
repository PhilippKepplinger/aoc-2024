package at.pkepp.puzzle24

class Bit(val name: String,
          var value: Byte? = null) {

    fun isSet(): Boolean {
        return value != null
    }
}