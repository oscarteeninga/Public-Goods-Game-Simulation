package player

import community.Community
import player.personality.Personality
import player.personality.Personality.Neutral

import scala.util.Random

case class Ordinary(id: String, community: Community) extends Player {
  override val personality: Personality = Neutral

  override def vote: Option[String] = {
    if (Random.nextDouble() > 0.25 && candidatesSympathize.nonEmpty)
      Some(community.candidates(Math.abs(Random.nextInt()) % candidatesSympathize.size)).map(_.id)
    else None
  }
}
