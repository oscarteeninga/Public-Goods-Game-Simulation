package game

import community.Community

object Department extends App {
  val manhattan = Community.create(10.0, 1.5, 2, 4, 3, 0)

  manhattan.play(10)
}
