package mn.lau.scullin.player

import mn.lau.scullin.card.Card

class DiscardPile {

  private var _cards: List[Card] = List()

  def cards = _cards

  def placeCardsOnTop(placedCards: List[Card]) {
    _cards = cards ::: placedCards
  }
}