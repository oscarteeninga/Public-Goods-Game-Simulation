package util



case class Stats(stats: List[Stat]) {

  def statsByRound: Map[Int, List[Stat]] = stats.groupBy(stat => stat.round)

  def personalityToAmount: List[(String, List[(Int, Double)])] = {
    stats.map {
      stat => (stat.personality.name, stat.round, stat.amount)
    }.groupBy(_._1).mapValues(stats => stats.map(s => (s._2, s._3)).sortBy(_._1)).toList
  }

  def personalityToEmotion: List[(String, List[(Int, Double)])] = {
    val angry = stats.map {
      stat => (stat.personality.name + "-angry", stat.round, stat.emotions.angry)
    }.groupBy(_._1).mapValues(stats => stats.map(s => (s._2, s._3.toDouble)))
    val thankfulness = stats.map {
      stat => (stat.personality.name + "-thankfulness", stat.round, stat.emotions.thankfulness)
    }.groupBy(_._1).mapValues(stats => stats.map(s => (s._2, s._3.toDouble)))
    (angry ++ thankfulness).toList
  }

  def personalityToPayIn: List[(String, List[(Int, Double)])] = {
    stats.map {
      stat => (stat.personality.name, stat.round, stat.payIn)
    }.groupBy(_._1).mapValues(stats => stats.map(s => (s._2, s._3)).sortBy(_._1)).toList
  }

  def angryToPayIn: List[(Int, Double)] = {
    stats.map {
      stat => (stat.emotions.angry, stat.payIn)
    }.groupBy(_._1).mapValues(payIns => payIns.map(_._2).sum / payIns.size).toList.sortBy(_._1)
  }

  def thankfulnessToPayIn: List[(Int, Double)] = {
    stats.map {
      stat => (stat.emotions.thankfulness, stat.payIn)
    }.groupBy(_._1).mapValues(payIns => payIns.map(_._2).sum / payIns.size).toList.sortBy(_._1)
  }

  def emotionToPayIn: List[(String, List[(Int, Double)])] = {
    List("angry" -> angryToPayIn, "thankfulness" -> thankfulnessToPayIn)
  }

  override def toString: String = {
    stats.mkString("\n")
  }
}
