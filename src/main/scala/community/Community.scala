package community

import player.{Altruist, Casual, Impostor, Ordinary, Player}
import util.Stat

case class Community(amount: Double, factor: Double) {

  private var players: List[Player] = Nil
  var statistics:  Map[Int, List[Stat]] = Map.empty

  def size: Int = players.size

  private def payIns(): Double = {
    players.map(_.payIn).sum
  }

  private def payOuts(pot: Double): Unit = {
    val payOff = pot * factor / size
    players.foreach(_.payout(payOff))
  }

  private def updateStatistics(roundIndex: Int): Unit = {
    statistics += (roundIndex -> players.map(player => Stat(player.personality, player.emotions, player.amount, player.lastPayIn, player.lastPayoff)))
  }

  def round(roundIndex: Int): List[Stat] = {
    val pot = payIns()
    payOuts(pot)
    updateStatistics(roundIndex)
    statistics(roundIndex)
  }

  def play(rounds: Int): Unit = {
    if (rounds > 0) {
      round(rounds)
      play(rounds-1)
    }
  }

  def withCasual(count: Int): Community = {
    addPlayers(count, Casual(_, this))
    this
  }

  def withAltruist(count: Int): Community = {
    addPlayers(count, Altruist(_, this))
    this
  }

  def withImpostor(count: Int): Community = {
    addPlayers(count, Impostor(_, this))
    this
  }

  def withOrdinary(count: Int): Community = {
    addPlayers(count, Ordinary(_, this))
    this
  }

  private def addPlayers(count: Int, playerCreation: String => Player): Unit = {
    players ++= (1 to count).toList.map(id => playerCreation(id.toString))
  }

  def getStatistic: Map[Int, List[Stat]] = statistics
}

object Community {
  val empty: Community = new Community(0, 0)
}
