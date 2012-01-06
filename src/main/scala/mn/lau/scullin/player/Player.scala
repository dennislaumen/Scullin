package mn.lau.scullin.player

class Player {

  val discardPile = new DiscardPile

  val deck = new Deck()

  var _hand = deck.drawNewHand

  def hand: Hand = _hand

  def discardHand {
    discardPile.placeCardsOnTop(hand.cards.toList)
    hand.discard
  }

  def drawNewHand {
    _hand = deck.drawNewHand
  }
}