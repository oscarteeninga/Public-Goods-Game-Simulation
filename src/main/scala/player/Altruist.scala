package player

import community.Community
import player.emotion.Personality

case class Altruist(id: String, community: Community) extends Player {
  override val personality: Personality = Personality.altruistic
}
