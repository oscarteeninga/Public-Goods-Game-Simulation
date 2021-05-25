package player.emotion

import util.Parameters.Emotion

case class Emotions(positive: List[Emotion], negative: List[Emotion]) {

  def all: List[Emotion] = positive ++ negative

  def emotionFactor: Double =
    (Emotion.defaultLevel + (positive.map(_.getLevel).sum / positive.size - negative.map(_.getLevel).sum / negative.size)) / Emotion.defaultLevel

  def update(payIn: Double, payOut: Double): Unit = {
    all.foreach(_.update(payIn, payOut))
  }
}
