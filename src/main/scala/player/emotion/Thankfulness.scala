package player.emotion

import util.Parameters.Emotion

case class Thankfulness() extends Emotion {
  override protected def better(): Unit = change(level + Emotion.step)

  override protected def worse(): Unit = change(level - Emotion.step)
}