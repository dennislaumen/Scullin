package mn.lau.scullin

import card.treasure.Copper
import card.victory.Estate
import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers
import player.Player

class GameTest extends FunSuite with ShouldMatchers {
  test("The player with the most victory points in his deck wins") {
    val game = new Game(4)
  }

  test("A game cannot be started with more than 4 players") {
    intercept[TooManyPlayersException] {
      val game = new Game(5)
    }
  }

  test("A game cannot be started with fewer than 2 players") {
    intercept[TooFewPlayersException] {
      val game = new Game(1)
    }
  }

  test("A game started with 4 players has 4 players") {
    val game = new Game(4)

    assert(game.players.size == 4, "The game started with " + game.players.size + " instead of 4.")
  }

  test("The game randomly determines the starting player") {
    val game = new Game(2)

    // Unfortunately, the randomness of the game's choice is not yet tested.
    assert(game.turn.player.isInstanceOf[Player], "The starting player is an instance of type " + game.turn.player.getClass.getName + " instead of Player.")
    assert(game.players.contains(game.turn.player), "The starting player, " + game.turn.player + ", is not a participant in this game.")
  }

  test("A player starts a game with 7 coppers and 3 estates in his shuffled deck") {
    val game = new Game(3)

    var numberOfCoppers = 0
    var numberOfEstates = 0

    val completeDeck = game.players(2).deck.cards ::: game.players(2).hand.cards.toList

    completeDeck.foreach(card =>
      if (card.isInstanceOf[Copper]) {
        numberOfCoppers += 1
      } else if (card.isInstanceOf[Estate]) {
        numberOfEstates += 1
      }
    )

    // Unfortunately, the randomness of the deck (shuffling) is not yet tested.
    assert(numberOfCoppers == 7, "The complete deck contained " + numberOfCoppers + " instead of 7.")
    assert(numberOfEstates == 3, "The complete deck contained " + numberOfEstates + " instead of 3.")
  }

  test("A player starts a game with a hand of 5 cards containing only coppers and estates from his starting deck") {
    val game = new Game(4)

    var numberOfCoppers = 0
    var numberOfEstates = 0

    game.players(3).deck.cards.foreach(card =>
      if (card.isInstanceOf[Copper]) {
        numberOfCoppers += 1
      } else if (card.isInstanceOf[Estate]) {
        numberOfEstates += 1
      }
    )

    assert(game.players(3).hand.cards.size == 5, "The hand contained " + game.players(3).hand.cards.size + " instead of 5.")
    assert((numberOfCoppers + numberOfEstates) == 5, "The hand contained cards other than coppers and estates.")
    assert(numberOfCoppers >= 2 && numberOfCoppers <= 7, "The hand contained " + numberOfCoppers + " coppers instead of a number between 2 and 7.")
    assert(numberOfEstates >= 0 && numberOfEstates <= 3, "The hand contained " + numberOfEstates + " estates instead of a number between 0 and 3.")
  }

  test("A player ends his turn, the next player is now the active player") {
    val game = new Game(2)

    val startingPlayer = game.turn.player

    game.endTurn

    val activePlayer = game.turn.player

    assert(activePlayer != startingPlayer, "The active player ended his turn but is still the active player")
  }

  test("After the last player ends his turn, the next active player is the first player") {
    val game = new Game(2)

    val startingPlayer = game.turn.player

    game.endTurn
    game.endTurn

    val activePlayer = game.turn.player

    assert(activePlayer == startingPlayer, "The starting player isn't the active player after all players have played a turn.")
  }

  test("After a player ends his first turn the cards in his hand are now on his discard pile") {
    val game = new Game(3)

    val startingPlayer = game.turn.player
    val startingPlayersHand = game.turn.player.hand.cards

    game.endTurn

    assert(startingPlayersHand.toSet equals startingPlayer.discardPile.cards.toSet, "The player's previous hand is not present on the discard pile.")
  }

  test("After a player ends his first turn the player has drawn a new hand from his deck") {
    val game = new Game(4)

    val startingPlayer = game.turn.player
    val startingPlayersHand = game.turn.player.hand.cards
    val cardsInNewHand = game.turn.player.deck.cards.take(5)

    game.endTurn

    assert(startingPlayer.hand.cards.size == 5, "The player's hand does not contain 5 cards but contains " + startingPlayer.hand.cards.size + " cards.")
    assert(!(startingPlayer.deck.cards.toSet.contains(startingPlayer.hand.cards.toSet)), "The player's deck still contains the player's hand.")
  }

  // test copper supply

/*/*  test("A game starts with a supply of 40 silver") {
    val game = new Game(3)

    assert(game.supply.silver.size == 40)
  }

  test("A game starts with a supply of 30 gold") {
    val game = new Game(2)

    assert(game.supply.gold.size == 30)
  }

  test("A game starts with a supply of 60 copper") {
    val game = new Game(2)

    assert(game.supply*/.copper.size == 60)
  }*/
  
  test("The active player buys a copper") {
    val game = new Game(4)

    // Skip the action phase
    game.nextPhase

    game.buyCard(classOf[Copper])
  }
}