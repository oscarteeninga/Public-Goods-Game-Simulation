package player.president

import player.Player

trait Candidate extends Player {

  def action: Unit
  def multiplier: Double
  def salary: Double

  override def vote: Option[String] = None

  override def updateSympathize: Unit = {

  }
}
