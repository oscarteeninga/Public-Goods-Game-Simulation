package player.president

import community.Community
import player.personality.Personality
import player.personality.Personality.Egoistic

case class Republican(id: String, community: Community) extends Candidate {

  override val personality: Personality = Egoistic

  override def action: Unit = {}

  override def multiplier: Double = 1.2

  override def salary: Double = 0.0

}
