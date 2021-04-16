package player

import community.Community
import player.emotion.Personality

case class Ordinary(id: String, community: Community) extends Player {

  override protected def b1: Double = 0.5

  override protected def b2: Double = 0.5

  override val personality: Personality = Personality.ordinary
}
