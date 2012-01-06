package mn.lau.scullin.player

import mn.lau.scullin.card.Card

class Hand(private var _cards: Set[Card]) {
  def cards = _cards

  def discard {
    _cards = Set()
  }

  def addCard(card: Card) {
    _cards = _cards + card
  }

  def takeCard(typeOfCard: Class[_ <: Card]): Card = {
    val cardsOfType = _cards.filter(c => c.getClass == typeOfCard)

    if (cardsOfType.size == 0) throw new IllegalArgumentException("No cards of type " + typeOfCard + " in hand")

    val playedCard = cardsOfType.toList(0)

    _cards = _cards - playedCard

    playedCard
  }
}