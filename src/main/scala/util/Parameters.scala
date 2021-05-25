package util

object Parameters {
  object Emotion {
    var defaultLevel: Double = 10.0
    var step: Double = 10.0
    var randomization: Int = 3
    var influence: Double = 0.1
  }

  object Personality {
    var max: Double = 1.1
    var min: Double = 0.9
    var neutral: Double = 1.0
  }

  object Community {
    var multiplier: Double = 2
    var impostors: Int = 10
    var cooperators: Int = 10
    var ordinaries: Int = 10
    var democrats: Int = 1
    var republicans: Int = 1
    var rounds: Int = 5
    var voting: Int = 5
    val amount: Double = 10.0
    val payIn: Double = 2.0
  }
}
