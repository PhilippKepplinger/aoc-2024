package at.pkepp.puzzle2

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.io.Source

class InputParser(val file: String) {

  def parse(): Array[mutable.Buffer[Long]] = {
    Source.fromResource(file)
      .mkString
      .split("\\n")
      .map(value => value
        .split("\\s")
        .map(v => v.toLong)
        .toBuffer
      )
  }
}
