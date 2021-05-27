package player

import community.Community
import player.personality.Personality
import player.personality.Personality.CooperatorPersonality

case class Cooperator(id: String, community: Community) extends Player {
  override val personality: Personality = CooperatorPersonality
}
