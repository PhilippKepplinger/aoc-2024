package at.pkepp.puzzle11

class Calculator {

  def countDigits(number: Long): Long = Math.log10(number).toLong + 1

  def evenDigits(number: Long): Boolean = {
    number != 0 && countDigits(number) % 2 == 0
  }

  def splitInHalf(number: Long): (Long, Long) = {
    def divison =  Math.pow(10, countDigits(number) / 2).toLong
    (number / divison, number % divison)
  }
}
