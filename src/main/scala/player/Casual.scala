package player

import community.Community
import player.emotion.Personality

import scala.math.{max, min}
import scala.util.Random

case class Casual(id: String, community: Community) extends Player {

  override val personality: Personality = Personality.ordinary

  override def contribution: Double =
    min(amount,  randomFactor * max(Random.nextDouble() * lastPayoff + Random.nextDouble() * (lastPayIn * community.size - lastPayoff) / (community.size - 1), 0))

  override def vote: Option[Int] = {
    if (Random.nextDouble() > 0.5) Some(Random.nextInt() % community.candidates.size) else None
  }
}
