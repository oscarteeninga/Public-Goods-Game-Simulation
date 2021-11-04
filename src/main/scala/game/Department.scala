package game

import community.Community
import util.{Parameters, Plot}

object Department extends App {
  val manhattan = Community(Parameters.Community.amount)
    .withCooperator(Parameters.Community.cooperators)
    .withImpostor(Parameters.Community.impostors)
    .withOrdinary(Parameters.Community.ordinaries)


  (1 to Parameters.Community.voting).foreach { _ =>
    manhattan.play(Parameters.Community.rounds)
    manhattan.voting()
  }

  Plot.plot_scatter(manhattan.getStats.emotionsToAmount, "Emotion intense", "Amount", manhattan.config)
  Plot.plot_scatter(manhattan.getStats.emotionsToPayIn, "Emotion intense", "Pay in", manhattan.config)
  Plot.plot_scatter(manhattan.getStats.personalitiesToPayIn, "Round", "Pay in", manhattan.config)
  Plot.plot_scatter(manhattan.getStats.personalityToAmount, "Round", "Amount", manhattan.config)
  Plot.plot_scatter(manhattan.getStats.personalityToEmotion, "Round", "Emotion intense", manhattan.config)
//  Plot.plot_scatter(manhattan.getStats.averageCandidatesSympathizeToRound, "Round", "Sympathize", manhattan.config)
}
