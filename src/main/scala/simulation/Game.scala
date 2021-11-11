package simulation

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.util.Timeout
import simulation.commons._
import simulation.player.{Cooperator, FreeRider, Player}

import scala.annotation.tailrec
import scala.concurrent.duration._
import scala.language.postfixOps
import scala.sys.process._

object Game extends App {

  Seq("bash", "-c", "rm -rf plot*.html").!

  val system = ActorSystem("simulation")
  implicit val timeout: Timeout = Timeout(1 seconds)

  def createPlayers(begin: Int, count: Int, creation: Int => Player): List[ActorRef] = {
    (begin to count).toList.map(id => system.actorOf(Props(creation(id)), "Player" + id))
  }

  def createMaster: ActorRef = system.actorOf(Props(Master()), "Master")

  val players = createPlayers(0, 0, FreeRider) ++ createPlayers(1, 4, Cooperator)
  val master = createMaster
  val gameInfo = GameInfo(players, 1.5, 10)

  @tailrec
  def rounds(r: Int): Unit = {
    master ! Start(gameInfo)
    Thread.sleep(100)
    if (r > 0) rounds(r - 1)
  }

  rounds(10)

  master ! Plot

  system.terminate()
}
