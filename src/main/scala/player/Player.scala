package player

import community.Community
import player.emotion.{Emotions, Personality}

import scala.math.{max, min}
import scala.util.Random

trait Player extends President {

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

  protected def randomFactor: Double = (2 + (0.5 - Random.nextDouble())) / 2

  protected def contribution: Double = {
    min(amount,  randomFactor * max(community.b1(this) * lastPayoff + community.b2(this) * (lastPayIn * community.size - lastPayoff) / (community.size - 1), 0))
  }

  def payIn: Double = {
    lastPayIn = contribution
    amount -= lastPayIn
    lastPayIn
  }

  def vote: Option[Int] = {
    Some(Random.nextInt() % community.size)
  }

  override def toString: String = {
    getClass.getSimpleName + id + ":\t" + amount
  }
}
