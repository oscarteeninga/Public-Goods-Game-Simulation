package player.emotion

import javax.json.{Json, JsonObject}

case class Personality(name: String, altruism: Double, egoism: Double, cooperating: Double) {

  lazy val features = Seq(altruism, egoism, cooperating)

  def toJson: JsonObject =
    Json.createObjectBuilder()
    .add("name", name)
    .add("altruism", altruism)
    .add("egoism", egoism)
    .add("cooperating", cooperating)
    .build()

  override def toString: String = toJson.toString

  def ==(other: Personality): Boolean = {
    this.egoism == other.egoism && this.altruism == other.altruism && this.cooperating == other.cooperating
  }
}

object Personality {
  val altruistic: Personality = Personality("altruistic", 1.0, 0.0, 0.75)
  val egoistic: Personality = Personality("egoistic", 0.0, 1.0, 0.25)
  val impostor: Personality = Personality("impostor", 0.0, 1.0, 0.0)
  val ordinary: Personality = Personality("ordinary", 0.5, 0.5, 0.5)
  val erudite: Personality = Personality("erudite", 0.5, 0.75, 1.0)

  lazy val personalities = Seq(altruistic, egoistic, impostor, ordinary, erudite)

  def fromFeatures(altruism: Double, egoism: Double, cooperating: Double): Personality = {
    personalities.find(Personality("", altruism, egoism, cooperating) == _).getOrElse(throw new ClassNotFoundException("Personality not found"))
  }
}
