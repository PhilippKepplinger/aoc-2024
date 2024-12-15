package puzzle11

import at.pkepp.puzzle11.InputParser
import org.scalatest.funspec.AnyFunSpec

class Puzzle11Test extends AnyFunSpec {

  describe("Input Parser") {
    it("should parse input") {
      val parser = InputParser("puzzle8/example-input-1.txt")
      assert(parser != null)
    }
  }
}
