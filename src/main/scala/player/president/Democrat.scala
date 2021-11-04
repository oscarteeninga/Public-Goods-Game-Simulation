package player.president
import community.Community
import player.personality.Personality
import player.personality.Personality.CooperatorPersonality

case class Democrat(id: Int, community: Community) extends Candidate {

  override val personality: Personality = CooperatorPersonality

  override def action(): Unit = {}

  override def salary: Double = 0.0
}
