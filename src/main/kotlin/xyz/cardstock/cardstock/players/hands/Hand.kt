/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.players.hands

import xyz.cardstock.cardstock.cards.Card

/**
 * A hand of cards, generally held by a [Player][xyz.cardstock.cardstock.players.Player]. See [PlayerHand].
 */
interface Hand<T : Card> : Iterable<T> {

    /**
     * Gets all cards in this hand.
     */
    val cards: List<T>

    /**
     * Gets the card at [index].
     */
    operator fun get(index: Int): T

    /**
     * Gets the size of this hand.
     */
    fun size(): Int

    /**
     * Adds [card] to this hand.
     */
    fun add(card: T)

    /**
     * Removes [card] from this hand.
     */
    fun remove(card: T)

    /**
     * Removes the card at [index].
     */
    fun remove(index: Int)

}
