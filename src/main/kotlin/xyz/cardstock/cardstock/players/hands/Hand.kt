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
public interface Hand<T : Card> : Iterable<T> {

    /**
     * Gets the card at [index].
     */
    public fun get(index: Int): T

    /**
     * Gets the size of this hand.
     */
    public fun size(): Int

    /**
     * Adds [card] to this hand.
     */
    public fun add(card: T)

    /**
     * Removes [card] from this hand.
     */
    public fun remove(card: T)

    /**
     * Removes the card at [index].
     */
    public fun remove(index: Int)

}
