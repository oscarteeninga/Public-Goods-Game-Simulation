package player.president

import player.Player

trait Candidate extends Player {

  def action(): Unit

  def salary: Double
}
