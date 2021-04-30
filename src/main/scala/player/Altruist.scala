package player

import community.Community
import player.emotion.Personality

case class Altruist(id: String, community: Community) extends Player {
  override val personality: Personality = Personality.altruistic

  override def action: Unit = {
    community.b2_factors = (1.0, 0.0, 1.0)
    community.b1_factors = (1.0, 0.0, 1.0)
  }
}
