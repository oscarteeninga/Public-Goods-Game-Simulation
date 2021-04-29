package game

import community.Community
import util.Plot


object Department extends App {
  val manhattan = Community(10.0, 1.5)
    .withAltruist(1)
    .withImpostor(7)
    .withOrdinary(4)

  manhattan.play(20)

  Plot.plot_scatter(manhattan.getStats.emotionToAmount, "Emotion intense", "Amount", manhattan.config)
  Plot.plot_scatter(manhattan.getStats.emotionToPayIn, "Emotion intense", "Pay in", manhattan.config)
  Plot.plot_scatter(manhattan.getStats.personalityToPayIn, "Round", "Pay in", manhattan.config)
  Plot.plot_scatter(manhattan.getStats.personalityToAmount, "Round", "Amount", manhattan.config)
  Plot.plot_scatter(manhattan.getStats.personalityToEmotion, "Round", "Emotion intense", manhattan.config)

}
