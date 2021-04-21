package community

import player.{Altruist, Casual, Impostor, Ordinary, Player}
import util.{Stat, Stats}

case class Community(amount: Double, factor: Double) {

  private var players: List[Player] = Nil
  var statistics:  List[Stat] = List.empty

  def size: Int = players.size

  private def payIns(): Double = {
    players.map(_.payIn).sum
  }

  private def payOuts(pot: Double): Unit = {
    val payOff = pot * factor / size
    players.foreach(_.payout(payOff))
  }

  private def updateStatistics(roundIndex: Int): Unit = {
    statistics ++= players.map(player => Stat(roundIndex, player.personality, player.emotions, player.amount, player.lastPayIn, player.lastPayoff))
  }

  def round(roundIndex: Int): Unit = {
    val pot = payIns()
    payOuts(pot)
    updateStatistics(roundIndex)
  }

  def play(rounds: Int): Unit = {
    (1 to rounds).toList.foreach(idx => round(idx))
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

  def config: String = {
    players.groupBy(_.personality.name).mapValues(_.size).map {
      case (name, size) => name + ": " + size
    }.mkString(", ")
  }

  def getStats: Stats = Stats(statistics.sortBy(_.round)(Ordering.Int))
}

object Community {
  val empty: Community = new Community(0, 0)
}
