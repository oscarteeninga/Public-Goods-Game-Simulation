package player.president

import player.Player
import player.emotion.Sympathize

trait Candidate extends Player {

  def action: Unit
  def salary: Double

  override val candidatesSympathize: Map[String, Sympathize] = Map.empty

  override def vote: Option[String] = None

  override def updateSympathize: Unit = { }
}
