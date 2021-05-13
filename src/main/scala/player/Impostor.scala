package player

import community.Community
import player.personality.Personality
import player.personality.Personality.Egoistic

case class Impostor(id: String, community: Community) extends Player {

  override val personality: Personality = Egoistic
}
