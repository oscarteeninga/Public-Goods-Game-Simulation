package util

import player.personality.Personality
import player.president.Candidate

case class Stat(round: Int, personality: Personality, emotions: List[(String, Double)], amount: Double, payIn: Double, payOff: Double, president: Option[Candidate])
