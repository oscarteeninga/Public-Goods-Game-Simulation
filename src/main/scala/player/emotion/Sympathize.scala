package player.emotion

import util.Parameters.Emotion

import scala.util.Random

case class Sympathize() extends Emotion {

  override def update(payIn: Double, payOut: Double): Unit = {
    if (payIn < payOut) better()
    else worse()
  }

  override protected def better(): Unit = change(level+Emotion.step)

  override protected def worse(): Unit = change(level-Emotion.step)

  def idle(): Unit = change(level + Random.nextDouble() * Emotion.step)
}
