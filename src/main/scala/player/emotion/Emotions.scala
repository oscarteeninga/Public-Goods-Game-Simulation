package player.emotion

case class Emotions(var angry: Int, var thankfulness: Int) {

  def update(payIn: Double, payOff: Double): Unit = {
    if (payIn/payOff > 1.5) updateAngry(1)
    if (payIn/payOff > 1.25) updateThankfulness(-1)
    if (payOff/payIn > 1.25) updateThankfulness(1)
    if (payOff/payIn > 1.5) updateAngry(-1)
  }

  private def updateAngry(value: Int): Unit = {
    angry = Math.max(0, Math.min(10, angry+value))
  }

  private def updateThankfulness(value: Int): Unit = {
    thankfulness = Math.max(0, Math.min(10, thankfulness+value))
  }

  def emotionFactor: Double = {
    1.0 - 2*angry/20 + thankfulness/20
  }
}
