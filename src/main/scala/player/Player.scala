package player

import community.Community
import player.emotion.{Angry, Emotion}
import player.personality.Personality
import util.Parameters

import scala.math.{max, min}

trait Player {

  val id: Int
  val personality: Personality
  val community: Community

  val emotions: List[Emotion] = List(Angry(), Angry())

  var amount: Double = community.amount
  var lastPayoff: Double = Parameters.Community.payIn
  var lastPayIn: Double = Parameters.Community.payIn

  def payout(payoff: Double): Unit = {
    lastPayoff = payoff
    amount += payoff
    emotions.foreach(_.updateLevel(lastPayIn, lastPayoff))
  }

  def payIn: Double = {
    lastPayIn = min(amount, max(emotionFactor * personality.contribution * amount * 0.5, 0))
    amount -= lastPayIn
    lastPayIn
  }

  protected def emotionFactor: Double = emotions.foldLeft(1.0)((f, emotion) => f * emotion.getFactor)

  override def toString: String = {
    getClass.getSimpleName + id + ":\t" + amount
  }
}
