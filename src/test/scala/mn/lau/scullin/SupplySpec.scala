package mn.lau.scullin

import card.curse.Curse
import card.kingdom._
import card.treasure.Copper
import card.victory.{Province, Duchy, Estate}
import org.scalatest.{Spec, GivenWhenThen}
import player.Supply


class SupplySpec extends Spec with GivenWhenThen {

  val kingdomCards = Set[Class[_ <: KingdomCard]](
    classOf[Adventurer],
    classOf[Bureaucrat],
    classOf[Cellar],
    classOf[Chancellor],
    classOf[Chapel],
    classOf[CouncilRoom],
    classOf[Feast],
    classOf[Festival],
    classOf[Gardens],
    classOf[Laboratory]
  )

  describe("A supply") {

    it("should contain the appropriate number of copper cards") {

      given("a supply for a two-player game")
      val supply = new Supply(2, kingdomCards)

      then("the supply should contain 46 copper cards")
      assert(supply.numberOfCards(classOf[Copper]) == 46, "The number of coppers in the supply is " + supply.numberOfCards(classOf[Copper]))
    }

    // TODO : This spec sucks grammatically. Am I writing bad English or should the class under test be refactored?
    it("should be able to gained cards from") {

      given("a supply for a three-player game")
      val supply = new Supply(3, kingdomCards)

      when("a copper card is gained from it")
      val gainedCards = supply.gainCards(1, classOf[Copper]).toList

      then("just one card is gained from the supply")
      assert(gainedCards.size == 1, "The number of gained cards is " + gainedCards.size)

      and("the gained card is of the expected type")
      assert(gainedCards(0).isInstanceOf[Copper], "The gained card is a " + gainedCards(0).getClass)
    }

    it("should contain one less card of a certain type when it is gained from it") {

      given("a supply for a four-player game")
      val supply = new Supply(4, kingdomCards)

      when("a copper card is gained from it")
      supply.gainCards(1, classOf[Copper]).toList

      then("the number of copper cards in the supply is now 31")
      assert(supply.numberOfCards(classOf[Copper]) == 31, "The supply contains " + supply.numberOfCards(classOf[Copper]))
    }

    it("should contain 8 victory cards of each type in a two-player game") {

      given("a supply for a two-player game")
      val supply = new Supply(2, kingdomCards)

      then("the number of estate cards is 8")
      assert(supply.numberOfCards(classOf[Estate]) == 8, "The supply contains " + supply.numberOfCards(classOf[Estate]) + " estates")

      and("the number of duchy cards is 8")
      assert(supply.numberOfCards(classOf[Duchy]) == 8, "The supply contains " + supply.numberOfCards(classOf[Estate]) + " duchies")

      and("the number of province cards is 8")
      assert(supply.numberOfCards(classOf[Province]) == 8, "The supply contains " + supply.numberOfCards(classOf[Estate]) + " provinces")
    }

    it("should contain the 12 victory cards of each type in a three-player game") {

      given("a supply for a three-player game")
      val supply = new Supply(3,kingdomCards)

      then("the number of estate cards is 12")
      assert(supply.numberOfCards(classOf[Estate]) == 12, "The supply contains " + supply.numberOfCards(classOf[Estate]) + " estates")

      and("the number of duchy cards is 12")
      assert(supply.numberOfCards(classOf[Duchy]) == 12, "The supply contains " + supply.numberOfCards(classOf[Estate]) + " estates")

      and("the number of province cards is 12")
      assert(supply.numberOfCards(classOf[Province]) == 12, "The supply contains " + supply.numberOfCards(classOf[Estate]) + " estates")
    }

    it("should contain the 12 victory cards of each type in a four-player game") {

      given("a supply for a four-player game")
      val supply = new Supply(4,kingdomCards)

      then("the number of estate cards is 12")
      assert(supply.numberOfCards(classOf[Estate]) == 12, "The supply contains " + supply.numberOfCards(classOf[Estate]) + " estates")

      and("the number of duchy cards is 12")
      assert(supply.numberOfCards(classOf[Duchy]) == 12, "The supply contains " + supply.numberOfCards(classOf[Estate]) + " estates")

      and("the number of province cards is 12")
      assert(supply.numberOfCards(classOf[Province]) == 12, "The supply contains " + supply.numberOfCards(classOf[Estate]) + " estates")
    }

    it("should contain 10 curse cards in a two-player game") {

      given("a supply for a two-player game")
      val supply = new Supply(2, kingdomCards)

      then("the number of curse cards is 10")
      assert(supply.numberOfCards(classOf[Curse]) == 10, "The supply contains " + supply.numberOfCards(classOf[Estate]) + " curses")

    }

    it("should contain 10 curse cards in a three-player game") {

      given("a supply for a three-player game")
      val supply = new Supply(3, kingdomCards)

      then("the number of curse cards are 20")
      assert(supply.numberOfCards(classOf[Curse]) == 20, "The supply contains " + supply.numberOfCards(classOf[Estate]) + " curses")
    }

    it("should contain 30 curse cards in a four-player game") {

      given("a supply for a four-player game")
      val supply = new Supply(4, kingdomCards)

      then("the number of curse cards is 30")
      assert(supply.numberOfCards(classOf[Curse]) == 30, "The supply contains " + supply.numberOfCards(classOf[Estate]) + " curses")
    }

    it("should have the last trashed card on its trash pile") {

      given("a supply for a two-player game")
      val supply = new Supply(2, kingdomCards)

      when("a card is trashed")
      val trashedEstate = new Estate
      supply.trashCard(new Copper)
      supply.trashCard(trashedEstate)

      then("the trashed card should be on top of the trash pile")
      assert(supply.trashPile.take(1)(0) equals trashedEstate, "The first card on the trash pile is " + supply.trashPile.take(1))
    }

    it("should contain 10 cards of every regular kingdom card type") {

      given("a supply for an arbitrary number of players")
      val supply = new Supply(3, kingdomCards)

      then("it should contain 10 cards of every regular kingdom card type")
      assert(supply.numberOfCards(classOf[Adventurer]) == 10, "The supply contains " + supply.numberOfCards(classOf[Adventurer]) + " Adventurer cards")
      assert(supply.numberOfCards(classOf[Bureaucrat]) == 10, "The supply contains " + supply.numberOfCards(classOf[Bureaucrat]) + " Bureaucrat cards")
      assert(supply.numberOfCards(classOf[Cellar]) == 10, "The supply contains " + supply.numberOfCards(classOf[Cellar]) + " Cellar cards")
      assert(supply.numberOfCards(classOf[Chapel]) == 10, "The supply contains " + supply.numberOfCards(classOf[Chapel]) + " Chapel cards")
      assert(supply.numberOfCards(classOf[CouncilRoom]) == 10, "The supply contains " + supply.numberOfCards(classOf[CouncilRoom]) + " CouncilRoom cards")
      assert(supply.numberOfCards(classOf[Feast]) == 10, "The supply contains " + supply.numberOfCards(classOf[Feast]) + " Feast cards")
      assert(supply.numberOfCards(classOf[Festival]) == 10, "The supply contains " + supply.numberOfCards(classOf[Festival]) + " Festival cards")
      assert(supply.numberOfCards(classOf[Laboratory]) == 10, "The supply contains " + supply.numberOfCards(classOf[Laboratory]) + " Laboratory cards")
    }

    it("should contain 8 victory kingdom cards in a two-player game") {

      given("a supply for a two-player game")
      val supply = new Supply(2, kingdomCards)

      then("it should contain 8 victory kingdom cards")
      assert(supply.numberOfCards(classOf[Gardens]) == 8, "The supply contains " + supply.numberOfCards(classOf[Gardens]) + " Gardens cards")
    }
  }
}