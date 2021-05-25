package player.emotion

import util.Parameters.{Community, Emotion}

import scala.util.Random

abstract class Emotion {

  protected var level: Double = Emotion.defaultLevel

  def getLevel: Double = level

  def step: Double = {
    val distance = Math.abs(Emotion.defaultLevel - level)
    Emotion.step/(Emotion.defaultLevel * (1 + distance))
  }

  def update(payIn: Double, payOut: Double): Unit = {
    if (payIn * Community.multiplier < payOut) {
      if (Random.nextDouble() > 0.05) better() else worse()
    } else {
      if (Random.nextDouble() > 0.05) worse() else better()
    }
  }

  def randomize(): Unit = {
    Math.abs(Random.nextInt()) % Emotion.randomization match {
      case 0 => better()
      case 1 => worse()
      case _ =>
    }
  }

  protected def better(): Unit = {
    change(step)
  }

  protected def worse(): Unit = {
    change(-step)
  }

  def change(value: Double): Unit = {
    level += value
  }

  def toStat: (String, Double) = (getClass.getSimpleName, level)
}


