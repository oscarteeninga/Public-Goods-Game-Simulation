package community

import player.{Altruist, Casual, Impostor, Ordinary, Player}
import util.{Stat, Stats}

import scala.math.abs

case class Community(amount: Double, factor: Double) {

  private var players: List[Player] = Nil
  var president: Option[Int] = None
  var statistics:  List[Stat] = List.empty
  var b1_factors: (Double, Double, Double) = (0.75, 0.25, 1.0)
  var b2_factors: (Double, Double, Double) = (0.75, 0.25, 0.5)

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
    getPresident.foreach(_.action)
  }

  def voting: Unit = {
    president = Some(players.flatMap(_.vote).groupBy(identity).mapValues(_.size).toList.maxBy(_._2)._2)
  }

  def b(player: Player, factor: (Double, Double, Double)): Double = {
    player.emotions.emotionFactor * abs(player.personality.altruism * factor._1 + player.personality.cooperating * factor._2 - player.personality.egoism * factor._3) / 2
  }

  def b1(player: Player): Double = b(player, b1_factors)

  def b2(player: Player): Double = b(player, b2_factors)

  def play(rounds: Int, start: Int = 1): Unit = {
    (start to rounds).toList.foreach(idx => round(idx))
  }

  def getPresident: Option[Player] = {
    president.map(players)
  }

  def setPresident(id: Int): Unit = {
    president = Some(id)
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
