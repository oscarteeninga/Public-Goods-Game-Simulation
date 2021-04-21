package game

import community.Community
import util.Plot


object Department extends App {
  val manhattan = Community(10.0, 1.5)
    .withAltruist(2)
    .withImpostor(2)
    .withOrdinary(2)

  manhattan.play(20)

  Plot.plot_scatter(manhattan.getStats.emotionToPayIn, "Emotion intense", "Pay In", manhattan.config)
}
