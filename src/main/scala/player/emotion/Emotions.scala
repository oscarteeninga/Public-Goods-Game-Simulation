package player.emotion

import util.Parameters.Emotion

case class Emotions(positive: List[Emotion], negative: List[Emotion]) {

  def all: List[Emotion] = positive ++ negative

  def emotionFactor: Double =
    1 + (positive.map(_.getLevel).sum - negative.map(_.getLevel).sum) / Emotion.interval

  def update(payIn: Double, payOut: Double): Unit = {
    all.foreach(_.update(payIn, payOut))
  }
}
