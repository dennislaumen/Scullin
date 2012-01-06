package mn.lau.scullin

import card.kingdom.{Village, Festival}
import card.treasure.{Gold, Silver, Copper}
import card.victory.Duchy
import org.scalatest.{GivenWhenThen, Spec}
import player.Hand


class HandSpec extends Spec with GivenWhenThen  {

  describe("A hand") {

    it("should contain no more cards if it is discarded") {

      given("a hand with five arbitrary cards")
      val hand = new Hand(Set(new Copper, new Festival, new Village, new Silver, new Duchy))

      when("the hand is discarded")
      hand.discard

      then("the hand should contain no more cards")
      assert(hand.cards.isEmpty, "Hand contains " + hand.cards.size + " cards")

    }

    it("should contain a card which was just added to it") {

      given("a hand with five arbitrary cards")
      val hand = new Hand(Set(new Copper, new Festival, new Village, new Silver, new Duchy))

      when("an arbitrary card is added to it")
      val addedCard = new Gold
      hand.addCard(addedCard)

      then("that card should be in the hand")
      assert(hand.cards.contains(addedCard), "Added card is not in hand")

    }

    it("should not contain a card which was just taken from it") {

      given("a hand with five arbitrary cards")
      val hand = new Hand(Set(new Copper, new Festival, new Village, new Silver, new Duchy))

      when("a card is taken from it")
      val takenCard = hand.takeCard(classOf[Copper])

      then("that card should not be in the hand")
      assert(!hand.cards.contains(takenCard), "Taken card is still in hand")

    }
  }

}