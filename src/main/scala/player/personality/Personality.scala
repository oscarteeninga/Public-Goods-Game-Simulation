package player.personality

case class Personality(contribution: Double) {

  lazy val features = Seq(contribution)

  def name: String = getClass.getSimpleName

  def ==(other: Personality): Boolean = {
    this.contribution == other.contribution
  }
}

object Personality {

  object CooperatorPersonality extends Personality(1.25)

  object ImpostorPersonality extends Personality(0.75)

  object OrdinaryPersonality extends Personality(1.0)

  lazy val personalities: Seq[Personality] = Seq(CooperatorPersonality, ImpostorPersonality, OrdinaryPersonality)
}
