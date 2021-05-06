package player.president

import community.Community
import player.emotion.Personality

case class Republican(id: String, community: Community) extends President {

  override def action: Unit = {

  }

  override def factor: Double = 1.2

  override def salary: Double = 0.0

  override val personality: Personality = Personality.impostor

  override def vote: Option[Int] = None
}
