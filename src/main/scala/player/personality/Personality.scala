package player.personality

import util.Parameters

case class Personality(altruism: Double, egoism: Double) {

  lazy val features = Seq(altruism, egoism)

  def name: String = getClass.getSimpleName

  def ==(other: Personality): Boolean = {
    this.egoism == other.egoism && this.altruism == other.altruism
  }
}

object Personality {

  object Altruistic extends Personality(Parameters.Personality.max, Parameters.Personality.min)
  object Egoistic extends Personality(Parameters.Personality.min, Parameters.Personality.max)
  object Neutral extends Personality(Parameters.Personality.neutral, Parameters.Personality.neutral)

  lazy val personalities: Seq[Personality] = Seq(Altruistic, Egoistic, Neutral)

  def fromFeatures(altruism: Double, egoism: Double): Personality = {
    personalities.find(p => p.altruism == altruism && p.egoism == egoism).getOrElse(throw new ClassNotFoundException("Personality not found"))
  }
}
