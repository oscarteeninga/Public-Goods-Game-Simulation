package util

import player.emotion.{Emotions, Personality}
import player.president.Candidate

import javax.json.{Json, JsonObject}

case class Stat(round: Int, personality: Personality, emotions: Emotions, amount: Double, payIn: Double, payOff: Double, president: Candidate) {

  def toJson: JsonObject =
    Json.createObjectBuilder()
      .add("round", round)
      .add("personality", personality.toJson)
      .add("emotions", emotions.toJson)
      .add("amount", amount)
      .add("payIn", payIn)
      .add("payOff", payOff)
      .add("president", president.id)
      .build()

  override def toString: String = toJson.toString
}
