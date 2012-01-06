package mn.lau.scullin

import card.treasure.Copper
import card.victory.Estate
import org.scalatest.{Spec, GivenWhenThen}
import player.Player

class GameSpec extends Spec with GivenWhenThen {

  describe("A game") {

    it("should be won by the player with the most victory points in his deck") {

      given("a four-player game")
      val game = new Game(4)

      // TODO : write actions and assertions.
    }

    it("should not be started with more than four players") {

      given("a five-player game")

      then("a TooManyPlayersException will be thrown")
      intercept[TooManyPlayersException] {
        val game = new Game(5)
      }
    }

    it("should not be started with less than two players") {

      given("a singleplayer game")

      then("a TooFewPlayersException will be thrown")
      intercept[TooFewPlayersException] {
        val game = new Game(1)
      }
    }

    it("should be started with four players") {

      given("a four-player game")
      val game = new Game(4)

      then("the game contains four players")
      assert(game.players.size == 4, "The game started with " + game.players.size + " instead of 4.")
    }

    it("should have a randomly determined starting player") {

      given("a game")
      val game = new Game(2)

      then("a starting player is chosen from the players in the game")
      // TODO : Unfortunately, the randomness of the game's choice is not yet tested.
      assert(game.players.contains(game.turn.player), "The starting player, " + game.turn.player + ", is not a participant in this game.")
    }

    it("should have players with seven Copper cards and three Estate cards") {

      given("a game")
      var numberOfCoppers = 0
      var numberOfEstates = 0
      val game = new Game(3)

      val completeDeck = game.players(2).deck.cards ::: game.players(2).hand.cards.toList

      completeDeck.foreach(card =>
        if (card.isInstanceOf[Copper]) {
          numberOfCoppers += 1
        } else if (card.isInstanceOf[Estate]) {
          numberOfEstates += 1
        }
      )

      then("a player's deck should contain seven Copper cards")
      // TODO : Unfortunately, the randomness of the deck (shuffling) is not yet tested.
      assert(numberOfCoppers == 7, "The complete deck contained " + numberOfCoppers + " instead of 7.")

      and("a player's deck should contain three Estate cards")
      assert(numberOfEstates == 3, "The complete deck contained " + numberOfEstates + " instead of 3.")
    }

    it("should players with hands containing five cards containing only Copper and Estate cards") {

      given("a game")
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

      then("a player's hand contains five cards")
      assert(game.players(3).hand.cards.size == 5, "The hand contained " + game.players(3).hand.cards.size + " instead of 5.")

      and("a player's hand contains only Copper and Estate cards")
      assert((numberOfCoppers + numberOfEstates) == 5, "The hand contained cards other than coppers and estates.")

      and("a player's hand should contain between two and seven Copper cards")
      assert(numberOfCoppers >= 2 && numberOfCoppers <= 7, "The hand contained " + numberOfCoppers + " coppers instead of a number between 2 and 7.")

      and("a player's hand should contain between zero and three Estate cards")
      assert(numberOfEstates >= 0 && numberOfEstates <= 3, "The hand contained " + numberOfEstates + " estates instead of a number between 0 and 3.")
    }

    it("should give the turn to the next player when the active player ends his turn") {

      given("a game")
      val game = new Game(2)

      val startingPlayer = game.turn.player

      when("the starting player ends his turn")
      game.endTurn

      val activePlayer = game.turn.player

      then("the starting player is no longer the active player")
      assert(activePlayer != startingPlayer, "The active player ended his turn but is still the active player")

      and("another player is now the active player")
      // TODO : write assertion.
    }

    it("should give the turn to the first player after the last player ends his turn") {

      given("a game")
      val game = new Game(2)

      val startingPlayer = game.turn.player

      game.endTurn

      when("the last player ends his turn")
      game.endTurn

      val activePlayer = game.turn.player

      then("the first player is now the active player")
      assert(activePlayer == startingPlayer, "The starting player isn't the active player after all players have played a turn.")
    }

    it("should have its players put the cards in his hand on his discard pile at the end of their turn") {

      given("a game")
      val game = new Game(3)

      val startingPlayer = game.turn.player
      val startingPlayersHand = game.turn.player.hand.cards

      when("a player ends his turn")
      game.endTurn

      then("the player's hand is now on the discard pile")
      assert(startingPlayersHand.toSet equals startingPlayer.discardPile.cards.toSet, "The player's previous hand is not present on the discard pile.")
    }

    it("should have its players draw a new hand at the end of their turns") {

      given("a game")
      val game = new Game(4)

      val startingPlayer = game.turn.player
      val startingPlayersHand = game.turn.player.hand.cards
      val cardsInNewHand = game.turn.player.deck.cards.take(5)

      when("a player ends his turn")
      game.endTurn

      then("a player's hand contains five cards")
      assert(startingPlayer.hand.cards.size == 5, "The player's hand does not contain 5 cards but contains " + startingPlayer.hand.cards.size + " cards.")
      and("a player's hand does not contain cards from the previous hand")
      assert(!(startingPlayer.deck.cards.toSet.contains(startingPlayer.hand.cards.toSet)), "The player's deck still contains the player's hand.")
    }

  }
}