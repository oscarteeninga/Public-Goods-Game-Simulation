package player

import community.Community
import player.emotion.Personality

import scala.util.Random

case class Casual(id: String, community: Community) extends Player {

  override protected def b1: Double = Random.nextDouble()

  override protected def b2: Double = Random.nextDouble()

  override val personality: Personality = Personality.Ordinary
}
