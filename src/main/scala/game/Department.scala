package game

import community.Community

object Department extends App {
  val manhattan = Community(10.0)
    .withAltruist(7)
    .withImpostor(1)
    .withOrdinary(4)
    .withDemocrat
    .withRepublican

  manhattan.play(20)
  manhattan.voting

  val extendManhattan = manhattan
    .withImpostor(10)

  extendManhattan.play(20, start = 20)
  extendManhattan.voting

//  Plot.plot_scatter(manhattan.getStats.emotionToAmount, "Emotion intense", "Amount", manhattan.config)
//  Plot.plot_scatter(manhattan.getStats.emotionToPayIn, "Emotion intense", "Pay in", manhattan.config)
//  Plot.plot_scatter(manhattan.getStats.personalityToPayIn, "Round", "Pay in", manhattan.config)
//  Plot.plot_scatter(manhattan.getStats.personalityToAmount, "Round", "Amount", manhattan.config)
//  Plot.plot_scatter(manhattan.getStats.personalityToEmotion, "Round", "Emotion intense", manhattan.config)

}
