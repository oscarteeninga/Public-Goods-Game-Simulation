package player.president

import community.Community
import player.personality.Personality
import player.personality.Personality.ImpostorPersonality

case class Republican(id: Int, community: Community) extends Candidate {

  override val personality: Personality = ImpostorPersonality

  override def action(): Unit = {}

  override def salary: Double = 1.0

}
