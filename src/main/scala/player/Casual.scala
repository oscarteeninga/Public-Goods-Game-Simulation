package player

import community.Community
import player.emotion.Personality

import scala.math.{max, min}
import scala.util.Random

case class Casual(id: String, community: Community) extends Player {

  override def action: Unit = {
    community.b2_factors = (1.0, 0.0, 1.0)
    community.b1_factors = (1.0, 0.0, 1.0)
  }

  override def contribution: Double =
    min(amount,  randomFactor * max(Random.nextDouble() * lastPayoff + Random.nextDouble() * (lastPayIn * community.size - lastPayoff) / (community.size - 1), 0))

  override val personality: Personality = Personality.ordinary
}
