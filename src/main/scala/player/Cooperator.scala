package player

import community.Community
import player.personality.Personality
import player.personality.Personality.Altruistic

case class Cooperator(id: String, community: Community) extends Player {
  override val personality: Personality = Altruistic

  override def vote: Option[Int] = {
    val candidate = community.candidates.maxBy(_.multiplier)
    Some(community.candidates.indexOf(candidate))
  }

  override def updateSympathize: Unit = {

  }
}
