package at.pkepp.puzzle2

import scala.collection.mutable


class Calculator(val data: Array[mutable.Buffer[Long]]) {

  private val min = 1
  private val max = 3

  def countSafe(dampen: Boolean): Long = {
    data.count(list => isSafe(list, dampen))
  }

  private def isSafe(list: mutable.Buffer[Long],
                     dampen: Boolean): Boolean = {
    if (list.length <= 1) {
      return true
    }

    var dampened = false
    var safe = true
    val sign = if list.head > list.apply(1) then -1 else 1
    val minDiff = min * sign
    val maxDiff = max * sign

    for (i <- 0 until list.length - 1 if safe && !dampened) {
      val current = list.apply(i)
      val next = list.apply(i + 1)
      val diff = next - current

      if (sign < 0 && (diff > minDiff || diff < maxDiff)) {
        safe = false
      } else if (sign > 0 && (diff < minDiff || diff > maxDiff)) {
        safe = false
      }

      if (!safe && dampen) {
        val bufferBefore = list.clone
        val bufferCurrent = list.clone
        val bufferNext = list.clone

        if (i == 0) {
          bufferBefore.clear()
        } else {
          bufferBefore.remove(i - 1)
        }

        bufferCurrent.remove(i)
        bufferNext.remove(i + 1)

        // try again with the previous, current or next element removed.
        // This should be enough to resolve the failed attempt
        safe = (i > 0 && isSafe(bufferBefore, false))
          || isSafe(bufferCurrent, false)
          || isSafe(bufferNext, false)

        // no need to continue because the check for the initial list failed already
        dampened = true
      }
    }

    safe
  }
}
