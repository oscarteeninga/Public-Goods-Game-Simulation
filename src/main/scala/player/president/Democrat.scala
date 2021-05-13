package player.president
import community.Community
import player.personality.Personality
import player.personality.Personality.Altruistic

case class Democrat(id: String, community: Community) extends Candidate {

  override val personality: Personality = Altruistic

  override def action: Unit = {}

  override def multiplier: Double = 2.0

  override def salary: Double = 7.5
}
