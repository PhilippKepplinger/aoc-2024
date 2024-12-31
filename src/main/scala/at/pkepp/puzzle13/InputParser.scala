package at.pkepp.puzzle13

import scala.io.Source

class InputParser(val file: String) {

  def parse(offset: Long = 0L): Array[ClawMachine] = {
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
      .map(value => toClawMachine(value, offset))
  }

  private def toClawMachine(value: String,
                            offset: Long): ClawMachine = {
    val lines = value.split("\n")
    val aLine = lines.apply(0).split(',')
    val bLine = lines.apply(1).split(',')
    val priceLine = lines.apply(2).split(',')

    val a = new Vector(aLine.apply(0).toLong, aLine.apply(1).toLong)
    val b = new Vector(bLine.apply(0).toLong, bLine.apply(1).toLong)
    val p = new Vector(priceLine.apply(0).toLong + offset, priceLine.apply(1).toLong + offset)
    new ClawMachine(a, b, p)
  }
}
