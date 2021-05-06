package community

import player.president.{Democrat, Candidate, Republican}
import player.{Altruist, Casual, Impostor, Ordinary, Player}
import util.{Stat, Stats}

import scala.math.abs

case class Community(amount: Double) {

  var candidates: List[Candidate] = Nil
  var president: Option[Candidate] = None
  var citizen: List[Player] = Nil
  var statistics:  List[Stat] = List.empty
  var b1_factors: (Double, Double, Double) = (0.75, 0.25, 1.0)
  var b2_factors: (Double, Double, Double) = (0.75, 0.25, 0.5)

  def players: List[Player] = candidates ++ citizen

  def factor: Double = {
    president.map(_.factor).getOrElse(1.5)
  }

  private def payIns(): Double = {
    players.map(_.payIn).sum
  }

  private def payouts(pot: Double): Unit = {
    players.foreach(_.payout(pot / players.size))
  }

  private def payPresident(pot: Double): Double = {
    president match {
      case Some(president) =>
        val salary = president.salary
        president.payout(salary)
        president.action
        pot - salary
      case None => pot
    }
  }

  def round(roundIndex: Int): Unit = {
    val pot = factor * payIns()
    payouts(payPresident(pot))
    updateStatistics(roundIndex)
  }

  def voting(): Unit = {
    val votes = players.flatMap(_.vote).groupBy(identity).mapValues(_.size).toList
    if (votes.nonEmpty)
      president = Some(candidates(votes.maxBy(_._2)._1))
  }

  def b(player: Player, factor: (Double, Double, Double)): Double = {
    player.emotions.emotionFactor * abs(player.personality.altruism * factor._1 + player.personality.cooperating * factor._2 - player.personality.egoism * factor._3) / 2
  }

  def b1(player: Player): Double = b(player, b1_factors)

  def b2(player: Player): Double = b(player, b2_factors)

  def play(rounds: Int): Unit = {
    (statistics.size to (rounds + statistics.size)).toList.foreach(idx => round(idx))
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
    candidates ++= Seq(Republican(candidates.size.toString, this))
    this
  }

  def withDemocrat: Community = {
    candidates ++= Seq(Democrat(candidates.size.toString, this))
    this
  }

  def config: String = {
    players.groupBy(_.personality.name).mapValues(_.size).map {
      case (name, size) => name + ": " + size
    }.mkString(", ")
  }

  private def updateStatistics(roundIndex: Int): Unit = {
    statistics ++= players.map(player => Stat(roundIndex, player.personality, player.emotions, player.amount, player.lastPayIn, player.lastPayoff))
  }

  def getStats: Stats = Stats(statistics.sortBy(_.round))
}
