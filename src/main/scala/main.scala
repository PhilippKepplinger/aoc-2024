
import at.pkepp.puzzle11.Blinker

import scala.main

@main
def main(): Unit = {
  val blinker = new Blinker(12, "puzzle11-zero.txt")
//  val blinker = new Blinker("puzzle11.txt")
  val star1 = blinker.blinkTimes(25, true)
  println(star1.length)
  println()

  val star2 = blinker.blinkTimes(75)
  println(star2.length)
}
