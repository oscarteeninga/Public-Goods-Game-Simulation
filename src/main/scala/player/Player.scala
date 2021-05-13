package player

import community.Community
import player.emotion.{Angry, Emotions, Sympathize, Thankfulness}
import player.personality.Personality

import scala.math.{abs, max, min}
import scala.util.Random

trait Player {

  val id: String
  val personality: Personality
  val community: Community

  val candidatesSympathize: Map[String, Sympathize] = community.candidates.map(candidate => (candidate.id, Sympathize())).toMap

  def vote: Option[String] = {
    Some(candidatesSympathize.maxBy(_._2.getLevel)._1)
  }

  def updateSympathize: Unit = {
    community.president match {
      case Some(president) =>
        candidatesSympathize(president.id).update(lastPayIn, lastPayoff)
        candidatesSympathize.filterNot(_._1 == president.id).foreach(_._2.idle())
      case None =>
        candidatesSympathize.values.foreach(_.update(Random.nextDouble(), Random.nextDouble()))
    }
  }
  var amount: Double = community.amount
  var lastPayoff: Double = amount / 2
  var lastPayIn: Double = amount / 2

  var emotions: Emotions = Emotions(List(Angry()), List(Thankfulness()))

  private def updateEmotions: Unit = {
    emotions.update(lastPayIn, lastPayoff)
  }

  def payout(payoff: Double): Unit = {
    lastPayoff = payoff
    amount += payoff
    updateEmotions
    updateSympathize
  }

  protected def randomFactor: Double = (2 + (0.5 - Random.nextDouble())) / 2

  private def factor: Double = randomFactor * emotions.emotionFactor

  protected def contribution: Double = {
    min(amount,  factor * max(b1 * lastPayoff + b2 * (lastPayIn * community.size - lastPayoff) / (community.size - 1), 0))
  }

  def b(factors: (Double, Double)): Double = {
    abs(this.personality.altruism * factors._1 + this.personality.egoism * factors._2) / 2
  }

  def b1: Double = b(community.b1)

  def b2: Double = b(community.b2)

  def payIn: Double = {
    lastPayIn = contribution
    amount -= lastPayIn
    lastPayIn
  }

  override def toString: String = {
    getClass.getSimpleName + id + ":\t" + amount
  }
}
