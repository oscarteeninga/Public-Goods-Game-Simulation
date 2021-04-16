package util

object Stats {

  def groupByPersonality(stats: List[Stat]): List[Stat] = {
    stats.groupBy(_.personality).map { case (personality, statList) =>
      Stat(
        personality,
        statList.map(_.emotions).reduce(_ + _),
        statList.map(_.amount).sum / statList.size,
        statList.map(_.payIn).sum / statList.size,
        statList.map(_.payOff).sum / statList.size
      )
    }.toList
  }

  def statsToString(stats: List[Stat]): String = {
    stats.mkString("\n")
  }
}
