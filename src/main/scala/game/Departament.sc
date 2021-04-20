import community.Community
import util.Stats

import plotly._, element._, layout._, Plotly._

val manhattan = Community(10.0, 1.5)
  .withAltruist(1)
  .withCasual(1)
  .withImpostor(1)
  .withOrdinary(1)

manhattan.play(20)

manhattan.statistics.toList.sortBy(_._1)(Ordering.Int).map {
  case (round, stats) => "Runda " + round + "\n" + Stats.statsToString(Stats.groupByPersonality(stats))
}.foreach(println)

val trace1 = Scatter(
  Seq(1, 2, 3, 4),
  Seq(0, 2, 3, 5),
  fill = Fill.ToZeroY
)

val trace2 = Scatter(
  Seq(1, 2, 3, 4),
  Seq(3, 5, 1, 7),
  fill = Fill.ToNextY
)

val data = Seq(trace1, trace2)

plot(data)