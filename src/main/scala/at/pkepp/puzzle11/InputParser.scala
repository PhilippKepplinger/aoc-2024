package at.pkepp.puzzle11

import scala.io.Source

class InputParser(val file: String) {

  def parse(): Array[Long] = {
    Source.fromResource(file)
      .mkString
      .split("\\s")
      .map((stone) => stone.toLong)
  }
}
