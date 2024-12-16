package puzzle11

import at.pkepp.puzzle11.{Blinker, Calculator, InputParser}
import org.scalatest.funspec.AnyFunSpec

class Puzzle11Test extends AnyFunSpec {

  describe("Calulator") {
    it("test even digits") {
      val calculator = new Calculator()

      assert(!calculator.evenDigits(0))
      assert(!calculator.evenDigits(9))
      assert(calculator.evenDigits(10))
      assert(calculator.evenDigits(11))
      assert(calculator.evenDigits(19))
      assert(calculator.evenDigits(99))
      assert(!calculator.evenDigits(100))
      assert(!calculator.evenDigits(101))
    }

    it("test split in half") {
      val calculator = new Calculator()

      assert(calculator.splitInHalf(10) == (1, 0))
      assert(calculator.splitInHalf(11) == (1, 1))
      assert(calculator.splitInHalf(1001) == (10, 1))
      assert(calculator.splitInHalf(2024) == (20, 24))
      assert(calculator.splitInHalf(512072) == (512, 72))
    }
  }

  describe("Blinker") {
    it("test blink times") {
      val blinker = new Blinker(6, "puzzle11-example.txt")

      assert(blinker.format(blinker.blinkTimes(1)) == "253000 1 7")
      assert(blinker.format(blinker.blinkTimes(2)) == "253 0 2024 14168")
      assert(blinker.format(blinker.blinkTimes(3)) == "512072 1 20 24 28676032")
      assert(blinker.format(blinker.blinkTimes(4)) == "512 72 2024 2 0 2 4 2867 6032")
      assert(blinker.format(blinker.blinkTimes(5)) == "1036288 7 2 20 24 4048 1 4048 8096 28 67 60 32")
      assert(blinker.format(blinker.blinkTimes(6)) == "2097446912 14168 4048 2 0 2 4 40 48 2024 40 48 80 96 2 8 6 7 6 0 3 2")
    }
  }
}
