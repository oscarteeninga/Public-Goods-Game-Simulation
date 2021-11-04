package player.emotion

import util.Parameters
import util.Parameters.Emotion

import scala.util.Random

abstract class Emotion(isPositive: Boolean) {

  def updateLevel(payIn: Double, payOut: Double): Unit

  protected var level: Double = Parameters.Emotion.defaultLevel

  private val step = Parameters.Emotion.step

  private val sign: Double = if (isPositive) 1.0 else -1.0

  // It's between 0.5 and 1.5
  def getFactor: Double = Math.atan(sign*level)/Math.PI + 1

  def randomize(): Unit = {
    Math.abs(Random.nextInt()) % Emotion.randomization match {
      case 0 => level += step
      case 1 => level -= step
      case _ =>
    }
  }

  def toStat: (String, Double) = (getClass.getSimpleName, getFactor)
}

case class Angry() extends Emotion(false) {
  override def updateLevel(payIn: Double, payOut: Double): Unit = {
    level += Parameters.Emotion.step * (if (payIn > payOut) 1 else -1)
  }
}

case class Sympathize(candidateId: Int) extends Emotion(true) {
  override def updateLevel(payIn: Double, payOut: Double): Unit = {
    level += Parameters.Emotion.step * (if (payIn > payOut) 1 else -1)
  }
}


