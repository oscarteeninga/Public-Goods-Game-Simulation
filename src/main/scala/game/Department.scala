package game

import community.Community
import util.Stats

object Department extends App {
  val manhattan = Community(10.0, 1.5)
    .withAltruist(1)
    .withCasual(1)
    .withImpostor(1)
    .withOrdinary(1)

  manhattan.play(20)

  manhattan.statistics.toList.sortBy(_._1)(Ordering.Int).map {
    case (round, stats) => "Runda " + round + "\n" + Stats.statsToString(Stats.groupByPersonality(stats))
  }.foreach(println)
}
