package player

import community.Community
import player.emotion.{Emotions, Personality}

import scala.math.{abs, max, min}
import scala.util.Random

trait Player {

  val id: String
  val personality: Personality
  var emotions: Emotions = Emotions(5, 5)
  val community: Community
  val neighbourhood: Community = Community.empty

  var amount: Double = community.amount
  var lastPayoff: Double = 0
  var lastPayIn: Double = amount / 2

  def payout(payoff: Double): Unit = {
    lastPayoff = payoff
    amount += payoff
    emotions = emotions.update(lastPayIn, lastPayoff)
  }

  protected def b1: Double = emotions.emotionFactor * abs(personality.altruism * 0.75 + personality.cooperating * 0.25 - personality.egoism * 0.5) / 2

  protected def b2: Double = emotions.emotionFactor * abs(personality.altruism * 0.75 + personality.cooperating * 0.25 - personality.egoism * 1.0) / 2

  private def randomFactor: Double = (2 + (0.5 - Random.nextDouble())) / 2

  private def contribution: Double = {
    min(amount,  randomFactor * max(b1 * lastPayoff + b2 * (lastPayIn * community.size - lastPayoff) / (community.size - 1), 0))
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
