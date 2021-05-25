package util
import scala.collection.immutable

case class Stats(stats: List[Stat]) {

  def statsByRound: Map[Int, List[Stat]] = stats.groupBy(stat => stat.round)

  def personalityToAmount: List[(String, List[(Double, Double)])] = {
    stats.map {
      stat => (stat.personality.name, stat.round.toDouble, stat.amount)
    }.groupBy(_._1).mapValues(stats => stats.map(s => (s._2, s._3)).groupBy(_._1).mapValues(x => x.map(_._2).sum/x.size).toList.sortBy(_._1)).toList
  }

  def personalityToEmotion: List[(String, List[(Double, Double)])] = {
    val angry = stats.map {
      stat => (stat.personality.name + "-" + stat.emotions.last._1, stat.round.toDouble, stat.emotions.last._2)
    }.groupBy(_._1).mapValues(stats => stats.map(s => (s._2, s._3)).groupBy(_._1).mapValues(x => x.map(_._2).sum/x.size).toList.sortBy(_._1))
    val thankfulness = stats.map {
      stat => (stat.personality.name + "-" + stat.emotions.head._1, stat.round.toDouble, stat.emotions.head._2)
    }.groupBy(_._1).mapValues(stats => stats.map(s => (s._2, s._3)).groupBy(_._1).mapValues(x => x.map(_._2).sum/x.size).toList.sortBy(_._1))
    (angry ++ thankfulness).toList
  }

  def personalitiesToPayIn: List[(String, List[(Double, Double)])] = {
    stats.map {
      stat => (stat.personality.name, stat.round.toDouble, stat.payIn)
    }.groupBy(_._1).mapValues(stats => stats.map(s => (s._2, s._3)).groupBy(_._1).mapValues(x => x.map(_._2).sum/x.size).toList.sortBy(_._1)).toList
  }

  def emotionToPayIn(emotionIndex: Int): List[(Double, Double)] = {
    stats.map {
      stat => (stat.emotions(emotionIndex)._2.toInt, stat.payIn)
    }.groupBy(_._1).mapValues(payIns => payIns.map(_._2).sum / payIns.size).toList.map(v => (v._1.toDouble, v._2)).sortBy(_._1)
  }

  def emotionsToPayIn: List[(String, List[(Double, Double)])] = {
    List("angry" -> emotionToPayIn(1), "thankfulness" -> emotionToPayIn(0))
  }

  def emotionToAmount(emotionIndex: Int): List[(Double, Double)] = {
    stats.map {
      stat => (stat.emotions(emotionIndex)._2, stat.amount)
    }.groupBy(_._1).mapValues(payIns => payIns.map(_._2).sum / payIns.size).toList.sortBy(_._1)
  }

  def emotionsToAmount: List[(String, List[(Double, Double)])] = {
    List("angry" -> emotionToAmount(1), "thankfulness" -> emotionToAmount(0))
  }

  def averageCandidatesSympathizeToRound: List[(String, List[(Double, Double)])] = {
    val avg = stats.flatMap {
      stat => stat.sympathize.map { case (id, level) => (stat.round, id, level) }
    }.groupBy(_._2).mapValues(_.map(x => (x._1.toDouble, x._3)).groupBy(_._1).mapValues(v => v.map(_._2).sum/v.size).toList.sortBy(_._1)).toList
    avg
  }

  override def toString: String = {
    stats.mkString("\n")
  }
}
