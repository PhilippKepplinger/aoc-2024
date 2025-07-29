package at.pkepp.puzzle24

import kotlin.experimental.and
import kotlin.experimental.or
import kotlin.experimental.xor

class Operation(val a: Bit,
                val operator: String,
                val b: Bit,
                var output: Bit) {

    companion object {
        fun getCalculation(operator: String): (a: Byte, b: Byte) -> Byte {
            return when (operator) {
                "AND" -> { a: Byte, b: Byte -> a and b }
                "OR" -> { a: Byte, b: Byte -> a or b }
                "XOR" -> { a: Byte, b: Byte -> a xor b }
                else -> throw UnsupportedOperationException()
            }
        }
    }

    fun perform() {
        if (canPerform()) {
            val result = getCalculation(operator)(a.value!!, b.value!!)
            output.value = result
        }
    }

    fun canPerform(): Boolean {
        return a.isSet() && b.isSet()
    }

    fun isXOR(): Boolean {
        return operator == "XOR"
    }

    fun isAND(): Boolean {
        return operator == "AND"
    }

    fun isOR(): Boolean {
        return operator == "OR"
    }

    fun hasBits(a: Bit?, b: Bit?): Boolean {
        return (this.a == a || this.b == a) && (this.a == b || this.b == b)
    }
}