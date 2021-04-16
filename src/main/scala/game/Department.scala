package game

import community.Community

object Department extends App {
  val manhattan = Community(10.0, 1.5)
    .withAltruist(5)
    .withCasual(5)
    .withImpostor(5)
    .withOrdinary(5)

  manhattan.play(20)
}
