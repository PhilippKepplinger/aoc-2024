package puzzle13

import at.pkepp.puzzle13.InputParser
import org.scalatest.funspec.AnyFunSpec

class Puzzle13Test extends AnyFunSpec {
  describe("Part one Test") {
    it("Part 1") {
      val machines = new InputParser("puzzle13.txt").parse()

      val tokens = machines
        .map(machine => machine.tokens())
        .sum

      assert(tokens == 37128)
    }
  }

  describe("Part two Test") {
    it("Part 2") {
      val machines = new InputParser("puzzle13.txt").parse(10000000000000L)

      val tokens = machines
        .map(machine => machine.tokens())
        .sum

      assert(tokens == 74914228471331L)
    }
  }
}
