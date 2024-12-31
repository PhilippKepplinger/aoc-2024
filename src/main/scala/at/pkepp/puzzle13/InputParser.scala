package at.pkepp.puzzle13

import scala.io.Source

class InputParser(val file: String) {

  def parse(): Array[ClawMachine] = {
    Source.fromResource(file)
      .mkString
      .replace("Button A: ", "")
      .replace("Button B: ", "")
      .replace("Prize: ", "")
      .replace(" ", "")
      .replace("X+", "")
      .replace("Y+", "")
      .replace("X=", "")
      .replace("Y=", "")
      .split("\\n\\n")
      .map(value => toClawMachine(value))
  }

  private def toClawMachine(value: String): ClawMachine = {
    val unit = 0
    //    val unit = 10000000000000L
    // 52150630365188
    // 58376392290572

    val lines = value.split("\n")
    val aLine = lines.apply(0).split(',')
    val bLine = lines.apply(1).split(',')
    val priceLine = lines.apply(2).split(',')

    val a = new Vector(aLine.apply(0).toLong, aLine.apply(1).toLong)
    val b = new Vector(bLine.apply(0).toLong, bLine.apply(1).toLong)
    val p = new Vector(priceLine.apply(0).toLong + unit, priceLine.apply(1).toLong + unit)
    new ClawMachine(a, b, p)
  }
}
