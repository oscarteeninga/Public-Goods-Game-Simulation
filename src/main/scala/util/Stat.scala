package util

import player.emotion.{Emotions, Personality}

import javax.json.{Json, JsonBuilderFactory, JsonObject}

case class Stat(personality: Personality, emotions: Emotions, amount: Double, payIn: Double, payOff: Double) {

  def toJson: JsonObject =
    Json.createObjectBuilder()
      .add("personality", personality.toJson)
      .add("emotions", emotions.toJson)
      .add("amount", amount)
      .add("payIn", payIn)
      .add("payOff", payOff)
      .build()

  override def toString: String = toJson.toString
}
