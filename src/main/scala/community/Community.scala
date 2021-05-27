package community

import player.president.{Candidate, Democrat, Republican}
import player.{Cooperator, Impostor, Ordinary, Player}
import util.{Parameters, Stat, Stats}

case class Community(amount: Double) {

  var candidates: List[Candidate] = Nil
  var president: Option[Candidate] = None
  var citizen: List[Player] = Nil
  var statistics:  List[Stat] = List.empty

  var rounds = 1

  def size: Int = players.size

  def players: List[Player] = candidates ++ citizen

  def multiplier: Double = Parameters.Community.multiplier

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

  def round(): Unit = {
    updateStatistics(rounds)
    val pot = multiplier * payIns()
    payouts(payPresident(pot))
    rounds += 1
  }

  def voting(): Unit = {
    val votes = players.flatMap(_.vote).groupBy(identity).mapValues(_.size).toList
    if (votes.nonEmpty)
      president = candidates.find(_.id == votes.maxBy(_._2)._1)
  }

  def play(rounds: Int): Unit = {
    (1 to rounds).foreach(_ => round())
  }

  def withCooperator(count: Int): Community = {
    addPlayers(count, Cooperator(_, this))
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
    statistics ++= players.map(player =>
      Stat(
        roundIndex,
        player.personality,
        player.emotions.all.map(_.toStat),
        player.amount, player.lastPayIn, player.lastPayoff,
        player.candidatesSympathize.mapValues(_.getLevel).toList
      )
    )
  }

  def getStats: Stats = Stats(statistics.sortBy(_.round))
}
