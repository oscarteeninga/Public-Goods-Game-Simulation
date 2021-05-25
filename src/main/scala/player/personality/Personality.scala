package player.personality

import util.Parameters

case class Personality(contribution: Double) {

  lazy val features = Seq(contribution)

  def name: String = getClass.getSimpleName

  def ==(other: Personality): Boolean = {
    this.contribution == other.contribution
  }
}

object Personality {

  object Altruistic extends Personality(Parameters.Personality.max)
  object Egoistic extends Personality(Parameters.Personality.min)
  object Neutral extends Personality(Parameters.Personality.neutral)

  lazy val personalities: Seq[Personality] = Seq(Altruistic, Egoistic, Neutral)
}
