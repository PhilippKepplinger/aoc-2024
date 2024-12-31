package at.pkepp.puzzle11

import java.util
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

class RecursiveBlinker(file: String)
  extends Blinker(file) {

  private val memory = new util.HashMap[(Long, Int), Long]()

  override def blinkTimes(n: Int, debug: Boolean = false): Long = {
    memory.clear()

    val start = System.currentTimeMillis()

    val result = blinkParallel(initialStones, n)

    val end = System.currentTimeMillis()
    println(s"Blinking $n times got ${result} stones in  (${end - start} ms): ")

    result
  }

  private def blinkParallel(stones: Array[Long], n: Int): Long = {
    val futures = stones.toList.map(stone => Future {
      blink(stone, n, memory)
    })

    val combinedFutures = Future.sequence(futures)
    val blinks = Await.result(combinedFutures, Duration("1 day"))

    blinks.sum
  }

  private def blink(stone: Long,
                    n: Int,
                    memory: util.HashMap[(Long, Int), Long]): Long = {
    var res: Long = 0
    val key = (stone, n)

    if (memory.containsKey((stone, n))) {
      res = memory.get(key)
    } else if (n == 0) {
      res = 1
    } else if (stone == 0) {
      res = blink(1, n - 1, memory)
    } else if (calculator.evenDigits(stone)) {
      val (left, right) = calculator.splitInHalf(stone)
      res = blink(left, n - 1, memory) + blink(right, n - 1, memory)
    } else {
      res = blink(stone * 2024, n - 1, memory)
    }

    memory.put(key, res)
    res
  }
}
