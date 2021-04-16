package community

import player.{Altruist, Casual, Impostor, Ordinary, Player}

case class Community(amount: Double, factor: Double) {

  private var players: List[Player] = Nil
  private var depositsLogs: List[List[Double]] = Nil

  def size: Int = players.size

  def round(): Unit = {
    val deposits = players.map(_.payIn)
    depositsLogs ::= deposits
    val pot = deposits.foldRight(0.0)((deposit, pot) => pot + deposit)
    println("Pot: " + pot + "\n"  + statistic() + "\n")
    val payoff = factor * pot / size
    players.foreach(_.payout(payoff))
  }

  def play(rounds: Int): Unit = {
    (1 to rounds).toList.foreach(_ => round())
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

  private def statistic(): String = {
    players.groupBy(_.getClass.getSimpleName).map {
      case (name, players) => name + "[" + players.size + "]: " + players.map(_.amount).sum / players.size
    }.mkString("\n")
  }
}

object Community {
  val empty: Community = new Community(0, 0)
}
