package puzzle11

import at.pkepp.puzzle11.{BufferBlinker, Calculator, RecursiveBlinker}
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

  describe("Buffer Blinker") {
    it("test blink times") {
      val blinker = new BufferBlinker(12, "puzzle11-example.txt")

      assert(blinker.blinkTimes(1) == 3)
      assert(blinker.blinkTimes(2) == 4)
      assert(blinker.blinkTimes(3) == 5)
      assert(blinker.blinkTimes(4) == 9)
      assert(blinker.blinkTimes(5) == 13)
      assert(blinker.blinkTimes(6) == 22)
    }
  }

  describe("Recursive Blinker") {
    it("test blink times") {
      val blinker = new RecursiveBlinker("puzzle11-example.txt")

      assert(blinker.blinkTimes(1) == 3)
      assert(blinker.blinkTimes(2) == 4)
      assert(blinker.blinkTimes(3) == 5)
      assert(blinker.blinkTimes(4) == 9)
      assert(blinker.blinkTimes(5) == 13)
      assert(blinker.blinkTimes(6) == 22)
    }
  }

  describe("Blinker") {
    it("test all blinker same result") {
      val bufferBlinker = new BufferBlinker(12, "puzzle11-example.txt")
      val recursiveBlinker = new RecursiveBlinker("puzzle11-example.txt")

      for (i <- 1 until 25) {
        assert(bufferBlinker.blinkTimes(i) == recursiveBlinker.blinkTimes(i))
      }
    }
  }
}
