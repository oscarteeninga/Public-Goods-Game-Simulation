package community

import player.{Altruist, Casual, Impostor, Intelligent, Player}

trait Community {

  val amount: Double
  val factor: Double
  val players: List[Player]

  lazy val size: Int = players.size

  protected def round(): Unit = {
    val pot = players.foldRight(0.0)((player, pot) => pot + player.deposit)
    println("Pot: " + pot + "\n"  + state() + "\n")
    val payoff = factor * pot / size
    players.foreach(_.payout(payoff))
  }

  def play(rounds: Int): Unit = {
    (1 to rounds).toList.foreach(_ => round())
  }

  private def state(): String = {
    players.groupBy(_.getClass.getSimpleName).mapValues(_.map(_.amount).sum).map {
      case (c, avg) => c + ": " + avg
    }.mkString("\n")
  }
}

object Community {
  val empty: Community = new Community {
    override val amount: Double = 0.0
    override val factor: Double = 0.0
    override val players: List[Player] = Nil
  }

  def create(a: Double, f: Double, casual: Int, altruist: Int, impostor: Int, intelligent: Int): Community = new Community {
    override val amount: Double = a

    override val factor: Double = f

    override val players: List[Player] = {
      def toPlayers(count: Int, playerCreation: String => Player): List[Player] = {
        (1 to count).toList.map(id => playerCreation(id.toString))
      }
      toPlayers(casual, Casual(_, this)) ++
        toPlayers(altruist, Altruist(_, this)) ++
        toPlayers(impostor, Impostor(_, this)) ++
        toPlayers(intelligent, Intelligent(_, this))
    }
  }
}
