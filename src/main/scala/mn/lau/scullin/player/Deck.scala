package mn.lau.scullin.player

import collection.mutable.ListBuffer
import util.Random
import mn.lau.scullin.card.Card
import mn.lau.scullin.card.treasure.Copper
import mn.lau.scullin.card.victory.Estate

class Deck {

  private var _cards = drawStartingDeck()

  def cards = _cards

  private def drawStartingDeck() = {
    val startingDeck = new ListBuffer[Card]

    for (i <- 1 to 7) {
      startingDeck += new Copper
    }

    for (i <- 1 to 3) {
      startingDeck += new Estate
    }

    val shuffledStartingDeck = Random.shuffle(startingDeck)

    shuffledStartingDeck.toList
  }

  def drawNewHand() = {
    val cardsInHand = cards.take(5)
    _cards = cards.takeRight(cards.size - 5)
    new Hand(cardsInHand.toSet)
  }

}