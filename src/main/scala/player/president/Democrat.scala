package player.president
import community.Community
import player.emotion.Personality

case class Democrat(id: String, community: Community) extends President {

  override def action: Unit = {

  }

  override def factor: Double = 2.0

  override def salary: Double = 2.0

  override val personality: Personality = Personality.altruistic

  override def vote: Option[Int] = None
}
