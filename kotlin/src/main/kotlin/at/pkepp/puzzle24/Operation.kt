package at.pkepp.puzzle24

import kotlin.experimental.and
import kotlin.experimental.or
import kotlin.experimental.xor

class Operation(private val a: Bit,
                private val operator: String,
                private val b: Bit,
                private val output: Bit) {

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
}