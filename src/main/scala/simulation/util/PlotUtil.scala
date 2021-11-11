package simulation.util

import plotly.Plotly._
import plotly._
import simulation.commons.Round

object PlotUtil {

  def plot_scatter(rounds: List[Round]): Unit = {
    val seriesPerPlayer =
      rounds
        .reverse
        .zipWithIndex
        .flatMap { case (r, idx) => r.toStats.map(s => (s._1, (idx, s._2, s._3))) }
        .groupBy(_._1)
        .view
        .mapValues(data => data.map(d => d._2))
    seriesPerPlayer.toSeq.flatMap {
      case (id, data) =>
        val amount = data.map(d => (d._1, d._3)).sortBy(_._1).unzip
        val pay = data.map(d => (d._1, d._2)).sortBy(_._1).unzip
        Seq(
          Scatter().withX(amount._1).withY(amount._2).withName("Player " + id + " amount"),
          //          Scatter().withX(pay._1).withY(pay._2).withName("Player " + id + " payment")
        )
    }.plot(
      title = "Players amount"
    )
  }
}
