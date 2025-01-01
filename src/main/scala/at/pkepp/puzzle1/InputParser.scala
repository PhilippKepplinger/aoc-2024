package at.pkepp.puzzle1

import scala.io.Source

class InputParser(val file: String) {

  def parse(): (Array[Long], Array[Long]) = {
    val lines = Source.fromResource(file)
      .mkString
      .split("\\n")

    val leftList = new Array[Long](lines.length)
    val rightList = new Array[Long](lines.length)

    for (i <- 0 until lines.length) {
      val elements = lines.apply(i).split("\\s\\s\\s")
      leftList.update(i, elements.apply(0).toLong)
      rightList.update(i, elements.apply(1).toLong)
    }

    (leftList, rightList)
  }
}
