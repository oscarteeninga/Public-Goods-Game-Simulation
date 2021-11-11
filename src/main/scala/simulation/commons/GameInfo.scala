package simulation.commons

import akka.actor.ActorRef

case class GameInfo(players: List[ActorRef], multiplier: Double, startAmount: Double, pot: Int = 0)
