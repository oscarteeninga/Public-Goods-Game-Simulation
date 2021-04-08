package game

object Department extends App {

  val n = 10

  val players = (1 to n).map(id => Player(id.toString, 10.0, n)).toList

  def round(): Double = {
    val jackpot = players.foldRight(0.0)((player, pot) => pot + player.play)
    val payoff = 2 * jackpot / n
    players.foreach(_.update(payoff))
    jackpot
  }

  for (i <- 1 to 100) {
    val jackpot = round()
    println("Round " + i + ": " + jackpot)
    players.foreach(println)
    println("")
  }
}
