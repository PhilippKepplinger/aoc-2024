package puzzle23

import at.pkepp.puzzle23.InputParser
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class Test23 {

    @Test
    fun test_part_one_example() {
        val network = InputParser("23", true).parseFile()
        assertEquals(7, network.findSetsPartOne("t", 3))
    }

    @Test
    fun test_part_one_input() {
        val network = InputParser("23", false).parseFile()
        val sets = network.findSetsPartOne("t", 3)
        println(sets)
    }

    @Test
    fun test_part_two_example() {
        val network = InputParser("23", true).parseFile()
        val lanSet = network.findLargestSet("t")
        assertEquals("co,de,ka,ta", lanSet)
    }

    @Test
    fun test_part_two_input() {
        val network = InputParser("23", false).parseFile()
        val lanSet = network.findLargestSet("t")
        println(lanSet)

        assertNotEquals("ar,bx,cc,ew,fq,jz,lj,nx,og,rd,tj,ug,wd", lanSet)
        assertNotEquals("dh,ex,fg,gz,ho,ic,jb,jh,ni,pu,rw,sk,ty", lanSet)
        assertNotEquals("gv,hu,ii,ko,pm,pq,pr,rg,tb,tu,wi,wl,wm", lanSet)
    }
}