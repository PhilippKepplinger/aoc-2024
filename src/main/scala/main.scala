
import at.pkepp.puzzle2.{Calculator, InputParser}

import scala.main

@main
def main(): Unit = {
  val lists = new InputParser("puzzle2/input.txt").parse()
  val calculator = new Calculator(lists)

  val safe = calculator.countSafe(true)
  println(safe)
}
