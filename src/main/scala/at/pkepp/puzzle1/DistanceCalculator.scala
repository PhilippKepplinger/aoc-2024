package at.pkepp.puzzle1

import scala.collection.mutable

class DistanceCalculator(val lists: (Array[Long], Array[Long])) {

  def getDistance: Long = {
    val leftList = lists._1.sorted
    val rightList = lists._2.sorted

    var distance = 0L

    for (i <- leftList.indices) {
      val left = leftList.apply(i)
      val right = rightList.apply(i)
      distance += Math.abs(left - right)
    }

    distance
  }

  def getSimilarity: Long = {
    val records = new mutable.HashMap[Long, Long]()
    val leftList = lists._1.sorted
    val rightList = lists._2.sorted

    var similarity = 0L

    for (i <- leftList.indices) {
      val left = leftList.apply(i)
      var currentSimilarity = 0L

      if (records.contains(left)) {
        currentSimilarity = records.apply(left)
      } else {
        val rightTimes = rightList.count(v => v == left)
        currentSimilarity = left * rightTimes
        records.put(left, currentSimilarity)
      }

      similarity += currentSimilarity
    }

    similarity
  }
}
