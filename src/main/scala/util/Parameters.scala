package util

object Parameters {
  object Emotion {
    // Bazowy poziom emocji
    var defaultLevel: Double = 10.0
    // Rozmiar zmiany emocji po rundzie
    var step: Double = 10.0
    // Losowość zmiany emocji
    var randomization: Int = 3
    // Jaki wpływ na grę mają emocje gracza
    var influence: Double = 0.1
    // O ile mniejszy może być payout od payIn * multiplier, aby gracz był po rundzie zadowolony
    var threshold: Double = 0.95
  }

  object Personality {
    // Maksymalny poziom kontrybucji
    var max: Double = 1.25
    /// Minimalny poziom kontrybucji
    var min: Double = 0.75
    // Neutralny poziom kontrybucji
    var neutral: Double = 1.0
  }

  object Community {
    // Współczynnik mnożący pieniądze
    var multiplier: Double = 3
    // Liczba oszukujących graczy
    var impostors: Int = 1
    // Liczba współpracujących graczy
    var cooperators: Int = 10
    // Liczba standardowych graczy
    var ordinaries: Int = 1
    // Liczba kandydatów demokratów
    var democrats: Int = 1
    // Liczba kandydatów republikanów
    var republicans: Int = 1
    // Liczba rund między głosowaniem
    var rounds: Int = 5
    // Liczba głosowań
    var voting: Int = 20
    // Wstępny stan konta grazca
    val amount: Double = 10.0
    // Bazowa liczba wpłacana przez gracza
    val payIn: Double = 2.0
  }
}
