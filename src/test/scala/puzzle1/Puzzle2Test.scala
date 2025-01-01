package puzzle1

import at.pkepp.puzzle2.{Calculator, InputParser}
import org.scalatest.funspec.AnyFunSpec

class Puzzle2Test extends AnyFunSpec {

  describe("Calculator") {
    it("test safe") {
      val input = new InputParser("puzzle2-example.txt").parse()
      val calculator = new Calculator(input)

      assert(calculator.countSafe(false) == 2)
      assert(calculator.countSafe(true) == 4)
    }
  }
}
