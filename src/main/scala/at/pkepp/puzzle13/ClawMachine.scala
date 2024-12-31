package at.pkepp.puzzle13

class Vector(val x: Long, val y: Long) {
  override def toString: String = f"(${x.toString}, $y)"
}

class Buttons(val A: Double, val B: Double) {
  override def toString: String = f"($A, $B)"
}

class ClawMachine(val a: Vector,
                  val b: Vector,
                  val P: Vector) {
  val costA = 3
  val costB = 1

  def tokens(): Long = {
    val buttons = getButtons()
    val solvable = isSolvable(buttons)
    val valid = isValid(buttons)
    val tokens = if solvable && valid
    then Math.round(buttons.A * costA + buttons.B * costB)
    else 0

    if (valid && !solvable) {
      print("ERROR, solvable but not valid: ")
      print("A=")
      print(a)
      print(", B=")
      print(b)
      print(", Price=")
      print(P)
      print(", Result=")
      print(buttons)
      print(", Tokens=")
      println(tokens)
      println()
    }

    if (solvable && !valid) {
      print("ERROR, solvable but not valid: ")
      print("A=")
      print(a)
      print(", B=")
      print(b)
      print(", Price=")
      print(P)
      print(", Result=")
      print(buttons)
      print(", Tokens=")
      println(tokens)
      println()

    } else if (solvable && valid) {
      print("tokens = ")
      print(tokens)
      print(", ")
      println(buttons)
    }

    tokens
  }

  private def getButtons(): Buttons = {
    val vB = getB()
    new Buttons(getAFromB(vB), vB)
  }

  private def getAFromB(vB: Double): Double = {
    (P.y / a.y.toDouble) - vB * (b.y / a.y.toDouble)
  }

  private def getB(): Double = {
    (P.x * a.y - a.x * P.y) / (b.x * a.y - b.y * a.x)
  }

  private def isValid(buttons: Buttons): Boolean = {
    val precision = 0.000001
    val calculatedX = buttons.A * a.x + buttons.B * b.x
    val calculatedY = buttons.A * a.y + buttons.B * b.y

    val diffX = Math.abs(calculatedX - P.x)
    val diffY = Math.abs(calculatedY - P.y)

    val xOK = diffX < precision
    val yOK = diffY < precision

    val valid = xOK && yOK

    if (!valid && isSolvable(buttons)) {
      println()
      println("Invalid solution found: ")
      println(f"diff X = $diffX")
      println(f"diff Y = $diffY")
      println(f"calculated X = $calculatedX, Price X = ${P.x}")
      println(f"calculated Y = $calculatedY, Price Y = ${P.y}")
    }

    valid
  }

  private def isSolvable(buttons: Buttons): Boolean = {
    hasNoDecimals(buttons.A) &&
      hasNoDecimals(buttons.B) &&
      buttons.A >= 0 &&
      buttons.B >= 0
  }

  private def hasNoDecimals(value: Double): Boolean = {
    val diff = value - value.toLong
    diff <= 0.000001 || diff >= 0.999999
  }
}
