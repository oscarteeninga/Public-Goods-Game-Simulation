package player.emotion

trait Personality {
  val altruism: Double
  val egoism: Double
  val cooperating: Double
}

object Personality {
  object Altruistic extends Personality {
    override val altruism: Double = 1.0
    override val egoism: Double = 0.0
    override val cooperating: Double = 0.75
  }

  object Egoistic extends Personality {
    override val altruism: Double = 0.0
    override val egoism: Double = 1.0
    override val cooperating: Double = 0.25
  }

  object Impostor extends Personality {
    override val altruism: Double = 0.0
    override val egoism: Double = 1.0
    override val cooperating: Double = 0.0
  }

  object Ordinary extends Personality {
    override val altruism: Double = 0.5
    override val egoism: Double = 0.5
    override val cooperating: Double = 0.5
  }

  object Erudite extends Personality {
    override val altruism: Double = 0.5
    override val egoism: Double = 0.75
    override val cooperating: Double = 1.0
  }
}
