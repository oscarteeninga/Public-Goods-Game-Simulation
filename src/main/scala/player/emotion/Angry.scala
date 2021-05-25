package player.emotion

case class Angry() extends Emotion {
  override protected def better(): Unit = super.worse()

  override protected def worse(): Unit = super.better()
}
