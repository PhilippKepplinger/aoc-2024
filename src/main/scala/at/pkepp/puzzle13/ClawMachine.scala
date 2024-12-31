package at.pkepp.puzzle13

class Vector(val x: BigDecimal, val y: BigDecimal) {
  override def toString: String = f"(${x.toString}, $y)"
}

class Buttons(val A: BigDecimal, val B: BigDecimal) {
  override def toString: String = f"($A, $B)"
}

class ClawMachine(val a: Vector,
                  val b: Vector,
                  val P: Vector) {
  val costA = 3
  val costB = 1

  def tokens(): BigDecimal = {
    val buttons = getButtons()
    val solvable = hasExactResult(buttons)
    val valid = isValid(buttons)

    if solvable && valid
    then buttons.A * costA + buttons.B * costB
    else BigDecimal.exact(0)
  }

  private def getButtons(): Buttons = {
    val vB = getB()
    new Buttons(getAFromB(vB), vB)
  }

  private def getAFromB(vB: BigDecimal): BigDecimal = {
    (P.y / a.y) - vB * (b.y / a.y)
  }

  private def getB(): BigDecimal = {
    (P.x * a.y - a.x * P.y) / (b.x * a.y - b.y * a.x)
  }

  private def isValid(buttons: Buttons): Boolean = {
    val precision = 0.000001
    val calculatedX = buttons.A * a.x + buttons.B * b.x
    val calculatedY = buttons.A * a.y + buttons.B * b.y

    val diffX = calculatedX - P.x
    val diffY = calculatedY - P.y

    val xOK = diffX < precision
    val yOK = diffY < precision

    xOK && yOK
  }

  private def hasExactResult(buttons: Buttons): Boolean = {
    hasNoDecimals(buttons.A) &&
      hasNoDecimals(buttons.B) &&
      buttons.A >= 0 &&
      buttons.B >= 0
  }

  private def hasNoDecimals(value: BigDecimal): Boolean = {
    val diff = value - value.toLong
    diff <= 0.000001 || diff >= 0.999999
  }
}
