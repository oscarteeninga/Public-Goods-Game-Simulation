package player

import community.Community
import player.emotion.Personality

case class Altruist(id: String, community: Community) extends Player {
  override val personality: Personality = Personality.altruistic

  override def vote: Option[Int] = {
    val candidate = community.candidates.maxBy(_.factor)
    Some(community.candidates.indexOf(candidate))
  }
}
