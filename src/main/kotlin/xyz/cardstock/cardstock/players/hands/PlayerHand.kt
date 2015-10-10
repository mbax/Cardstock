/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.players.hands

import com.google.common.collect.Lists
import xyz.cardstock.cardstock.cards.Card
import java.util.Objects

/**
 * A hand designed to be held by a [Player][xyz.cardstock.cardstock.players.Player].
 */
open class PlayerHand<T : Card> : Hand<T> {

    private val cards: MutableList<T> = Lists.newArrayList()

    operator override fun get(index: Int) = this.cards[index]

    override fun size() = this.cards.size()

    override fun add(card: T) {
        this.cards.add(card)
    }

    override fun remove(card: T) {
        this.cards.remove(card)
    }

    override fun remove(index: Int) {
        this.cards.remove(index)
    }

    override fun iterator() = this.cards.iterator()

    override fun equals(other: Any?) = if (other == null || other !is PlayerHand<*>) false else this.cards.equals(other.cards)

    override fun hashCode() = Objects.hash(this.cards)
}
