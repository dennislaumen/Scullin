package mn.lau.scullin.player

import mn.lau.scullin.{TooManyPlayersException, TooFewPlayersException}
import mn.lau.scullin.card.kingdom.KingdomCard
import mn.lau.scullin.card.Card
import mn.lau.scullin.card.treasure.{Gold, Silver, Copper}
import mn.lau.scullin.card.curse.Curse
import mn.lau.scullin.card.victory.{Victory, Province, Duchy, Estate}


class Supply(val numberOfPlayers: Int, kingdomCards: Set[Class[_ <: KingdomCard]]) {
  if (numberOfPlayers < 2) throw new TooFewPlayersException

  if (numberOfPlayers > 4) throw new TooManyPlayersException

  private var _cards: Set[Card] = Set()

  private var _trashPile: List[Card] = List()

  placeCardsInSupply

  private def placeCardsInSupply() {
    val numberOfVictoryCards = if (numberOfPlayers == 2) 8 else 12
    val numberOfCurseCards = if (numberOfPlayers == 2) 10 else if (numberOfPlayers == 3) 20 else 30

    // A Dominion game contains 60 copper cards. Every player receives 7 coppers at the start of the game.
    // Another possible solution would've been to let the player draw his coppers from the initialized supply but this
    // would've become a problem with estates.
    val numberOfCopper = 60 - (7 * numberOfPlayers)

    1 to numberOfCopper foreach { _ => _cards += new Copper }
    1 to 40 foreach { _ => _cards += new Silver }
    1 to 30 foreach { _ => _cards += new Gold }

    1 to numberOfVictoryCards foreach { _ => _cards += new Estate }
    1 to numberOfVictoryCards foreach { _ => _cards += new Duchy }
    1 to numberOfVictoryCards foreach { _ => _cards += new Province }

    1 to numberOfCurseCards foreach { _ => _cards += new Curse }

    kingdomCards foreach  {
      k =>
        if (k.newInstance.isInstanceOf[Victory]) {
          1 to numberOfVictoryCards foreach  { _ => _cards += k.newInstance}
        } else {
          1 to 10 foreach { _ => _cards += k.newInstance }
        }
    }
  }

  def trashPile = _trashPile

  def gainCards(amount: Int, cardType: Class[_ <: Card]): Set[Card] = {
    val cardsOfType = _cards.filter(c => c.getClass == cardType)

    if (cardsOfType.size < amount) {
      throw new IllegalArgumentException("Not enough cards in supply.")
    }

    val cardsToGain = cardsOfType.take(amount)
    _cards = _cards -- cardsToGain

    cardsToGain
  }

  def numberOfCards(typeOfCard: Class[_ <: Card]): Int = {
    val cardsOfType = _cards.filter(c => c.getClass == typeOfCard)
    cardsOfType.size
  }

  def trashCard(card: Card) {
    _trashPile = card +: _trashPile
  }
}