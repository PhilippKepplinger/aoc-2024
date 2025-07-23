package puzzle21

import at.pkepp.puzzle21.CodeDecoder
import at.pkepp.puzzle21.InputParser
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Test {

    val parser = InputParser("21")

    @Test
    fun test() {
        val decoder = CodeDecoder(2)

        val res = decoder.decode(
            listOf(
                parser.parseLine("029A"),
                parser.parseLine("980A"),
                parser.parseLine("179A"),
                parser.parseLine("456A"),
                parser.parseLine("379A"),
            )
        )

        assertEquals(126384, res)
    }
}