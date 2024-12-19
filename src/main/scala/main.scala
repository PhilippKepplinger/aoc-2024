
import at.pkepp.puzzle11.RecursiveBlinker

import scala.main

@main
def main(): Unit = {
  //  val blinker = new BufferBlinker(12, "puzzle11.txt")
  val blinker = new RecursiveBlinker("puzzle11.txt")

  val star1 = blinker.blinkTimes(25)
  println(star1)
  println()

  val star2 = blinker.blinkTimes(75)
  println(star2)
}
