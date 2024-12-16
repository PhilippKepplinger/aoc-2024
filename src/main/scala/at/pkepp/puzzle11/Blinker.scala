package at.pkepp.puzzle11

import scala.collection.mutable
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

class Blinker(val threads: Int, val file: String) {

  private val initialStones: Array[Long] = InputParser(file).parse()
  private val calculator: Calculator = new Calculator

  def blinkTimes(n: Int, debug: Boolean = false): mutable.Buffer[Long] = {
    var stones = initialStones.clone().toBuffer

    for (i <- 1 to n) {
      val start = System.currentTimeMillis()

//      blink(stones)
      stones = blinkParallel(stones)

      val end = System.currentTimeMillis()
      val time = end - start
      if (debug) {
        print(s"Blink $i = ${stones.length} ($time ms): ")
        println(format(stones))
      } else {
        println(s"Blink $i = ${stones.length} ($time ms): ")
      }
    }

    stones
  }

  private def blinkParallel(stones: mutable.Buffer[Long]):  mutable.Buffer[Long] = {
    val blinkedStones = mutable.Buffer.empty[Long]
    val chunks = stones.grouped(threads)

    val futures = chunks.map((chunk: mutable.Buffer[Long]) => Future {
      blink(chunk)
    })

    val result = Await.result(Future.sequence(futures), Duration("1 day"))
    result.foreach(blinkedStones ++= _)
    blinkedStones
  }

  private def blink(stones: mutable.Buffer[Long]): mutable.Buffer[Long] = {
    var i = 0

    while (i < stones.length) {
      if (stones(i) < 0) throw new Exception("negative stone detected")

      if (stones(i) == 0) {
        stones.update(i, 1)
      } else if (calculator.evenDigits(stones(i))) {
        val (left, right) = calculator.splitInHalf(stones(i))
        stones.update(i, left)
        stones.insert(i + 1, right)
        i += 1
      } else {
        stones.update(i, stones(i) * 2024)
      }

      i += 1
    }

    stones
  }

  def format(stones: mutable.Buffer[Long]): String = {
    stones.mkString(" ")
  }
}