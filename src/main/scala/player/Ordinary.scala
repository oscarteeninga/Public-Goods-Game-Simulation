package player

import community.Community
import player.personality.Personality
import player.personality.Personality.Neutral

import scala.util.Random

case class Ordinary(id: String, community: Community) extends Player {
  override val personality: Personality = Neutral

  override def vote: Option[Int] = {
    if (Random.nextDouble() > 0.5) Some(Random.nextInt() % community.candidates.size) else None
  }

  override def updateSympathize: Unit = {

  }
}
