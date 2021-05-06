package player

import community.Community
import player.emotion.Personality

case class Impostor(id: String, community: Community) extends Player {

  override val personality: Personality = Personality.impostor

  override def vote: Option[Int] = {
    val candidate = community.candidates.minBy(_.factor)
    Some(community.candidates.indexOf(candidate))
  }
}
