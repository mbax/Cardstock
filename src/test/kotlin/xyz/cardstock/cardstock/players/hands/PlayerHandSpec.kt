/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.players.hands

import org.jetbrains.spek.api.shouldThrow
import xyz.cardstock.cardstock.MavenSpek
import xyz.cardstock.cardstock.implementations.cards.TestCard
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PlayerHandSpec : MavenSpek() {
    override fun test() {
        given("a PlayerHand") {
            val playerHand = PlayerHand<TestCard>()
            on("construction") {
                it("should have no cards") {
                    assertEquals(0, playerHand.size())
                }
            }
        }
        given("another PlayerHand") {
            val playerHand = PlayerHand<TestCard>()
            on("adding a card") {
                val card = TestCard(1)
                playerHand.add(card)
                it("should have one card") {
                    assertEquals(1, playerHand.size())
                }
                it("should have one card with a point value of 1") {
                    assertEquals(1, playerHand.filter { it.points == 1 }.size)
                }
                it("should be accessible from the get function") {
                    assertTrue(card === playerHand[0])
                }
                it("should be accessible from the cards list") {
                    assertTrue(card === playerHand.cards[0])
                }
                it("should not be removable from the cards list") {
                    shouldThrow(UnsupportedOperationException::class.java) {
                        (playerHand.cards as MutableList<TestCard>).removeAt(0)
                    }
                }
            }
            on("comparing equality with another hand with the same card") {
                val anotherHand = PlayerHand<TestCard>().apply { this.add(playerHand[0]) }
                it("should be equal") {
                    assertEquals(playerHand, anotherHand)
                }
                it("should have the same hash code") {
                    assertEquals(playerHand.hashCode(), anotherHand.hashCode())
                }
            }
        }
        given("one more PlayerHand") {
            val playerHand = PlayerHand<TestCard>()
            val card = TestCard(1)
            playerHand.add(card)
            on("removing the card by index") {
                playerHand.remove(0)
                it("should have zero cards") {
                    assertEquals(0, playerHand.size())
                }
                it("should throw an IndexOutOfBoundsException when accessed") {
                    shouldThrow(IndexOutOfBoundsException::class.java) {
                        playerHand[0]
                    }
                }
            }
        }
        given("one last PlayerHand") {
            val playerHand = PlayerHand<TestCard>()
            val card = TestCard(1)
            playerHand.add(card)
            on("removing the card by the card reference") {
                playerHand.remove(card)
                it("should have zero cards") {
                    assertEquals(0, playerHand.size())
                }
                it("should throw an IndexOutOfBoundsException when accessed") {
                    shouldThrow(IndexOutOfBoundsException::class.java) {
                        playerHand[0]
                    }
                }
            }
        }
    }
}
