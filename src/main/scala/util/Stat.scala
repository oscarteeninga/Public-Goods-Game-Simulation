package util

import player.personality.Personality

case class Stat(
  round: Int,
  personality: Personality,
  emotions: List[(String, Double)],
  amount: Double, payIn: Double,
  payOff: Double,
  sympathize: List[(String, Double)])
