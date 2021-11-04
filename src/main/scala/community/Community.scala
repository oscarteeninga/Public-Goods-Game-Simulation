package community

import player.president.{Candidate, Democrat, Republican}
import player.Player
import player.citizen.{Citizen, Cooperator, Impostor, Ordinary}
import util.{Parameters, Stat, Stats}

case class Community(amount: Double) {

  var candidates: List[Candidate] = Nil
  var president: Option[Candidate] = None
  var citizen: List[Citizen] = Nil
  var statistics:  List[Stat] = List.empty

  var rounds = 1

  def multiplier: Double = Parameters.Community.multiplier

  private def payIns(): Double = {
    citizen.map(_.payIn).sum
  }

  private def payouts(pot: Double): Unit = {
    citizen.foreach(_.payout(pot / citizen.size))
    citizen.foreach(_.updateSympathize())
  }

  private def payPresident(pot: Double): Double = {
    president match {
      case Some(president) =>
        val salary = president.salary
        president.payout(salary)
        president.action()
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
    val votes = citizen.flatMap(_.vote).groupBy(identity).mapValues(_.size).toList
    if (votes.nonEmpty)
      president = candidates.find(_.id == votes.maxBy(_._2)._1)
  }

  def play(rounds: Int): Unit = {
    (1 to rounds).foreach(_ => round())
  }

  def withCooperator(count: Int): Community = {
    addCitizen(count, Cooperator(_, this))
    this
  }

  def withImpostor(count: Int): Community = {
    addCitizen(count, Impostor(_, this))
    this
  }

  def withOrdinary(count: Int): Community = {
    addCitizen(count, Ordinary(_, this))
    this
  }

  private def addCitizen(count: Int, citizenCreation: Int => Citizen): Unit = {
    citizen ++= (1 to count).toList.map(id => citizenCreation(id))
  }

  def withRepublican: Community = {
    candidates ++= Seq(Republican(candidates.size, this))
    this
  }

  def withDemocrat: Community = {
    candidates ++= Seq(Democrat(candidates.size, this))
    this
  }

  def config: String = {
    citizen.groupBy(_.personality.name).mapValues(_.size).map {
      case (name, size) => name + ": " + size
    }.mkString(", ")
  }

  private def updateStatistics(roundIndex: Int): Unit = {
    statistics ++= citizen.map(citizen => {
      if (citizen.amount < 0) println(citizen.amount)
      Stat(
        roundIndex,
        citizen.personality,
        citizen.emotions.map(_.toStat),
        citizen.amount,
        citizen.lastPayIn,
        citizen.lastPayoff,
        citizen.candidatesSympathize.map(symp => (symp.candidateId.toString, symp.getFactor))
      )
    })
  }

  def getStats: Stats = Stats(statistics.sortBy(_.round))
}
