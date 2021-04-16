package player.emotion

import javax.json.{Json, JsonObject}

case class Emotions(angry: Int, thankfulness: Int) {

  def update(payIn: Double, payOff: Double): Emotions = {
    if (payOff < 1.5 * payIn) {
      Emotions(between(angry + 1), between(thankfulness - 1))
    } else {
      Emotions(between(angry - 1), between(thankfulness + 1))
    }
  }

  private def between(value: Int, min: Int = 0, max: Int = 10): Int = {
    if (value < min) min
    else if (value > max) max
    else value
  }

  def emotionFactor: Double = {
    1.0 + (thankfulness - angry)/10
  }

  def +(other: Emotions): Emotions = {
    Emotions((this.angry + other.angry)/2, (this.thankfulness + other.thankfulness)/2)
  }

  def toJson: JsonObject = Json.createObjectBuilder().add("angry", angry).add("thanfulness", thankfulness).build()

  override def toString: String = {
    toJson.toString
  }
}
