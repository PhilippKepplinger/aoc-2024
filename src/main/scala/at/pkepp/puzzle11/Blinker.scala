package at.pkepp.puzzle11

abstract class Blinker(val file: String) {

  protected val initialStones: Array[Long] = InputParser(file).parse()
  protected val calculator: Calculator = new Calculator

  def blinkTimes(n: Int, debug: Boolean): Long
}
