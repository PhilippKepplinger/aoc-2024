package puzzle13

import at.pkepp.puzzle13.InputParser
import org.scalatest.funspec.AnyFunSpec

class Puzzle13Test extends AnyFunSpec {
  describe("Part one Test") {
    val machines = new InputParser("puzzle13.txt").parse()

    val tokens = machines
      .map(machine => machine.tokens())
      .sum

    assert(tokens == 37128)
  }
}
