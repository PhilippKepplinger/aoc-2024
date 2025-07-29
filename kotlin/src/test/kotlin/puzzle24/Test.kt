package puzzle24

import at.pkepp.puzzle24.InputParser
import at.pkepp.puzzle24.Operation
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Test24 {

    @Test
    fun test_operations() {
        val xor = Operation.getCalculation("XOR")
        assertEquals(0b0, xor(0b0, 0b0))
        assertEquals(0b0, xor(0b1, 0b1))
        assertEquals(0b1, xor(0b0, 0b1))
        assertEquals(0b1, xor(0b1, 0b0))

        val or = Operation.getCalculation("OR")
        assertEquals(0b0, or(0b0, 0b0))
        assertEquals(0b1, or(0b1, 0b1))
        assertEquals(0b1, or(0b0, 0b1))

        val and = Operation.getCalculation("AND")
        assertEquals(0b0, and(0b0, 0b1))
        assertEquals(0b0, and(0b0, 0b0))
        assertEquals(0b1, and(0b1, 0b1))
    }

    @Test
    fun test_part_one_example() {
        val circuit = InputParser("24", "example").parseFile()

        assertEquals("2024", circuit.getResult())
    }

    @Test
    fun test_part_one_input() {
        val circuit = InputParser("24", "input").parseFile()

        assertEquals("59336987801432", circuit.getResult())
    }

    @Test
    fun test_part_two_input() {
        val circuit = InputParser("24", "input").parseFile()

        assertEquals("59609718189912", circuit.getCorrectedResult())
    }
}