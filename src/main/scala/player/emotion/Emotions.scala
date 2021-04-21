package player.emotion

import javax.json.{Json, JsonObject}
import scala.util.Random

case class Emotions(angry: Int, thankfulness: Int) {

  def update(payIn: Double, payOff: Double): Emotions = {
    val rand = Math.abs(Random.nextInt()) % 3
    if (payOff < 1.5 * payIn) {
      rand match {
        case 0 => Emotions(between(angry + 1), between(thankfulness - 1))
        case 1 => Emotions(between(angry + 1), between(thankfulness))
        case 2 => Emotions(between(angry), between(thankfulness - 1))
        case 4 => Emotions(between(angry), between(thankfulness))
      }
    } else {
      rand match {
        case 0 => Emotions(between(angry - 1), between(thankfulness + 1))
        case 1 => Emotions(between(angry - 1), between(thankfulness))
        case 2 => Emotions(between(angry), between(thankfulness + 1))
        case 4 => Emotions(between(angry), between(thankfulness))
      }
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
