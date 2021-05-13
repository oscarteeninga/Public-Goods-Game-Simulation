package player.emotion

import util.Parameters.Emotion

import scala.util.Random

abstract class Emotion {

  protected var level: Double = (Emotion.max - Emotion.min) / 2

  def getLevel: Double = level.doubleValue()

  def update(payIn: Double, payOut: Double): Unit = {
    if (payIn * 1.5 < payOut) better()
    else if (payIn > 1.5 * payOut) worse()
    else randomize()
  }

  protected def randomize(): Unit = {
    Math.abs(Random.nextInt()) % Emotion.randomization match {
      case 0 => better()
      case 1 => worse()
      case _ =>
    }
  }

  protected def better(): Unit

  protected def worse(): Unit

  protected def change(value: Double): Unit = {
    if (value > Emotion.min && value < Emotion.max) level = value
  }

  def toStat: (String, Double) = (getClass.getSimpleName, level)
}


