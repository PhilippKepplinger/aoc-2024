
import at.pkepp.puzzle1.{DistanceCalculator, InputParser}

import scala.main

@main
def main(): Unit = {
  val lists = new InputParser("puzzle1/input.txt").parse()
  val calculator = new DistanceCalculator(lists)

  val distance = calculator.getDistance
  println(distance)

  val similarity = calculator.getSimilarity
  println(similarity)

}
