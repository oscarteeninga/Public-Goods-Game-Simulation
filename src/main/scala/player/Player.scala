package player

import community.Community
import player.emotion.{Angry, Emotions, Sympathize, Thankfulness}
import player.personality.Personality
import util.Parameters

import scala.math.{abs, max, min}
import scala.util.Random

trait Player {

  val id: String
  val personality: Personality
  val community: Community

  val candidatesSympathize: Map[String, Sympathize] = community.candidates.map(candidate => (candidate.id, Sympathize())).toMap

  def vote: Option[String] = {
    if (candidatesSympathize.isEmpty) None
    else Some(candidatesSympathize.maxBy(_._2.getLevel)._1)
  }

  def updateSympathize: Unit = {
    community.president match {
      case Some(president) =>
        candidatesSympathize(president.id).update(lastPayIn, lastPayoff)
        candidatesSympathize.filterNot(_._1 == president.id).foreach(_._2.randomize())
      case None =>
        candidatesSympathize.values.foreach(_.randomize())
    }
  }
  var amount: Double = community.amount
  var lastPayoff: Double = Parameters.Community.payIn
  var lastPayIn: Double = Parameters.Community.payIn

  var emotions: Emotions = Emotions(List(Thankfulness()), List(Angry()))

  private def updateEmotions: Unit = {
    emotions.update(lastPayIn, lastPayoff)
  }

  def payout(payoff: Double): Unit = {
    lastPayoff = payoff
    amount += payoff
    updateEmotions
    updateSympathize
  }

  protected def randomFactor: Double = (10 + (0.5 - Random.nextDouble())) / 10

  protected def emotionFactor: Double = emotions.emotionFactor

  protected def contributionFactor: Double = personality.contribution

  private def factor: Double = randomFactor * emotionFactor * contributionFactor

  protected def contribution: Double = {
    min(amount, max(factor * Parameters.Community.payIn, 0))
  }

  def payIn: Double = {
    lastPayIn = contribution
    amount -= lastPayIn
    lastPayIn
  }

  override def toString: String = {
    getClass.getSimpleName + id + ":\t" + amount
  }
}
