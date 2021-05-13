package player

import community.Community
import player.personality.Personality
import player.personality.Personality.Egoistic

case class Impostor(id: String, community: Community) extends Player {

  override val personality: Personality = Egoistic

  override def vote: Option[Int] = {
    val candidate = community.candidates.minBy(_.multiplier)
    Some(community.candidates.indexOf(candidate))
  }

  override def updateSympathize: Unit = {

  }
}
