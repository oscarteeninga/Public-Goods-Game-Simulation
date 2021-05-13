package util

object Parameters {
  object Emotion {
    var max: Double = 10.0
    var min: Double = 0.0
    var step: Double = 1.0
    var randomization: Int = 3
    var interval: Double = max - min
  }

  object Personality {
    var max: Double = 1.5
    var min: Double = 0.75
    var neutral: Double = 1.0
  }

  object Community {
    var multiplier: Double = 1.1
    var impostors: Int = 50
    var cooperators: Int = 50
    var ordinaries: Int = 50
    var democrats: Int = 1
    var republicans: Int = 1
    var rounds: Int = 50
    var voting: Int = 5
    val amount: Double = 10.0
    var b1: (Double, Double) = (1.5, 0.75)
    var b2: (Double, Double) = (0.75, 1.5)
  }
}
