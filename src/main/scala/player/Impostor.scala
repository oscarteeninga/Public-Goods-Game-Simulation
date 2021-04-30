package player

import community.Community
import player.emotion.Personality

case class Impostor(id: String, community: Community) extends Player {

  override val personality: Personality = Personality.impostor

  override def action: Unit = {
    community.b1_factors = (0.5, 0.5, 0.5)
    community.b2_factors = (0.5, 0.5, 0.5)
  }
}
