package mn.lau.scullin

import card.Card
import player.{Supply, Player}
import util.Random
import collection.mutable.ListBuffer

class Game(val numberOfPlayers: Int) {
  if (numberOfPlayers < 2) {
    throw new TooFewPlayersException
  }

  if (numberOfPlayers > 4) {
    throw new TooManyPlayersException
  }

  val players: List[Player] = getNewPlayers

  val supply = new Supply(numberOfPlayers, Set())

  private var _turn = new Turn(getStartingPlayer)

  private def getNewPlayers: List[Player] = {
    val newPlayers = new ListBuffer[Player]

    for (i <- 1 to numberOfPlayers) {
      newPlayers += new Player
    }

    newPlayers.toList
  }

  private def getStartingPlayer: Player = {
    players(Random.nextInt(numberOfPlayers - 1))
  }

  private def switchActivePlayer {
    var index = players.indexOf(turn.player) + 1

    if (index >= numberOfPlayers) {
      index = 0
    }

    _turn = new Turn(players(index))
  }

  def endTurn {
    turn.player.discardHand
    turn.player.drawNewHand

    switchActivePlayer
  }

  def turn = _turn

  def nextPhase {
    _turn = turn.nextPhase
  }

  def buyCard(cardType: Class[_ <: Card]) {
    turn.player.discardPile.placeCardsOnTop(supply.gainCards(1, cardType).toList)
  }
}