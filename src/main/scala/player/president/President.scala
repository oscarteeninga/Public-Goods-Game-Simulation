package player.president

import player.Player

trait President extends Player {
  def action: Unit
  def factor: Double
  def salary: Double
}
