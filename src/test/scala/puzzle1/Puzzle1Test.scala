package puzzle1

import at.pkepp.puzzle1.{DistanceCalculator, InputParser}
import org.scalatest.funspec.AnyFunSpec

class Puzzle1Test extends AnyFunSpec {

  describe("Distance Calculator") {
    it("test example distance") {
      val input = new InputParser("puzzle1-example.txt").parse()
      val calculator = new DistanceCalculator(input)

      assert(calculator.getDistance == 11)
    }

    it("test example similarity") {
      val input = new InputParser("puzzle1-example.txt").parse()
      val calculator = new DistanceCalculator(input)

      assert(calculator.getSimilarity == 31)
    }
  }
}
