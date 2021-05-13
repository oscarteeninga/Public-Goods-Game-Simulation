package player

import community.Community
import player.personality.Personality
import player.personality.Personality.Altruistic

import scala.util.Random

case class Cooperator(id: String, community: Community) extends Player {
  override val personality: Personality = Altruistic


}
