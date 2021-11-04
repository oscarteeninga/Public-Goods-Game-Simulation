package util

object Parameters {
  object Emotion {
    // Bazowy poziom emocji
    var defaultLevel: Double = 0.0
    // Rozmiar zmiany emocji po rundzie
    var step: Double = 0.25
    // Losowość zmiany emocji
    var randomization: Int = 10
  }

  object Community {
    // Współczynnik mnożący pieniądze
    var multiplier: Double = 1.1
    // Liczba oszukujących graczy
    var impostors: Int = 1
    // Liczba współpracujących graczy
    var cooperators: Int = 1
    // Liczba standardowych graczy
    var ordinaries: Int = 1
    // Liczba kandydatów demokratów
    var democrats: Int = 0
    // Liczba kandydatów republikanów
    var republicans: Int = 0
    // Liczba rund między głosowaniem
    var rounds: Int = 10
    // Liczba głosowań
    var voting: Int = 2
    // Wstępny stan konta grazca
    val amount: Double = 10.0
    // Bazowa liczba wpłacana przez gracza
    val payIn: Double = 2.0
  }
}
