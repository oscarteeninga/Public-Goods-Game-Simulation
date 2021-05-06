package community

import player.president.{Democrat, President, Republican}
import player.{Altruist, Casual, Impostor, Ordinary, Player}
import util.{Stat, Stats}

import scala.math.abs

case class Community(amount: Double) {

  var candidates: List[President] = Nil
  var president: Option[President] = None
  var citizen: List[Player] = Nil
  var statistics:  List[Stat] = List.empty
  var b1_factors: (Double, Double, Double) = (0.75, 0.25, 1.0)
  var b2_factors: (Double, Double, Double) = (0.75, 0.25, 0.5)

  def players: List[Player] = candidates ++ citizen

  def size: Int = citizen.size

  private def payIns(): Double = {
    players.map(_.payIn).sum
  }

  private def payOuts(pot: Double): Unit = {
    val payOff = pot * getFactor / size
    players.foreach(_.payout(payOff))
  }

  private def payPresident(pot: Double): Double = {
    president match {
      case Some(president) =>
        val salary = president.salary
        president.payout(salary)
        pot - salary
      case None => pot
    }
  }

  private def updateStatistics(roundIndex: Int): Unit = {
    statistics ++= players.map(player => Stat(roundIndex, player.personality, player.emotions, player.amount, player.lastPayIn, player.lastPayoff))
  }

  def round(roundIndex: Int): Unit = {
    val pot = payPresident(payIns())
    payOuts(pot)
    updateStatistics(roundIndex)
    president.foreach(_.action)
  }

  def voting: Unit = {
    val votes = players.flatMap(_.vote)
    if (votes.isEmpty) {
      president = None
    } else {
      val id = votes.groupBy(identity).mapValues(_.size).toList.sortBy(_._2).reverse.head._1
      president = Some(candidates(id))
    }
  }

  def b(player: Player, factor: (Double, Double, Double)): Double = {
    player.emotions.emotionFactor * abs(player.personality.altruism * factor._1 + player.personality.cooperating * factor._2 - player.personality.egoism * factor._3) / 2
  }

  def b1(player: Player): Double = b(player, b1_factors)

  def b2(player: Player): Double = b(player, b2_factors)

  def play(rounds: Int, start: Int = 1): Unit = {
    (start to rounds).toList.foreach(idx => round(idx))
  }

  def setPresident(id: Int): Unit = {
    president = Some(id).map(candidates)
  }

  def getFactor: Double = {
    president.map(_.factor).getOrElse(1.5)
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
    citizen ++= (1 to count).toList.map(id => playerCreation(id.toString))
  }

  def withRepublican: Community = {
    candidates ++= Seq(Republican((candidates.size).toString, this))
    this
  }

  def withDemocrat: Community = {
    candidates ++= Seq(Democrat((candidates.size).toString, this))
    this
  }

  def config: String = {
    citizen.groupBy(_.personality.name).mapValues(_.size).map {
      case (name, size) => name + ": " + size
    }.mkString(", ")
  }

  def getStats: Stats = Stats(statistics.sortBy(_.round)(Ordering.Int))
}
