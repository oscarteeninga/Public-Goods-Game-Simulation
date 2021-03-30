package game

object Department extends App {

  val players = (1 to 5).map(id => Player(id.toString, 10.0)).toList

  def round(): Double = {
    val jackpot = players.foldRight(0.0)((player, pot) => pot + player.play)
    val payoff = jackpot / players.size
    players.foreach(_.update(payoff))
    jackpot
  }

  for (i <- 1 to 10) {
    val jackpot = round()
    println("Round " + i + ": " + jackpot)
    players.foreach(println)
    println("")
  }
}
