package util

import plotly.Scatter
import plotly._, layout._, Plotly._

object Plot {
  def plot_scatter(series: List[(String, List[(Double, Double)])], x_name: String = "", y_name: String = "", title: String = ""): Unit = {
    series.map {
      case (name, values) =>
        val (xs, ys) = values.unzip
        Scatter(xs, ys, name = name)
    }.plot(
      xaxis = Axis(x_name),
      yaxis = Axis(y_name),
      title = title
    )
  }

  def plot_bar(series: List[(String, List[(Double, Double)])], x_name: String = "", y_name: String = "", title: String = ""): Unit = {
    series.map {
      case (name, s) =>
        val (xs, ys) = s.unzip
        Bar(xs, ys, name = name)
    }.plot(
      xaxis = Axis(x_name),
      yaxis = Axis(y_name),
      title = title
    )
  }
}
