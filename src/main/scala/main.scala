
import at.pkepp.puzzle13.InputParser

import scala.main

@main
def main(): Unit = {
  val machines = new InputParser("puzzle13/input.txt").parse(10000000000000L)

  val tokens = machines
    .map(machine => machine.tokens())
    .sum

  println(tokens)
}
