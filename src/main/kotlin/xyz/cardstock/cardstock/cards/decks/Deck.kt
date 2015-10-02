/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.cards.decks

import xyz.cardstock.cardstock.cards.Card
import xyz.cardstock.cardstock.cards.packs.CardPack

/**
 * A deck of cards. A deck contains the [Card]s from many [CardPack]s. Decks require no shuffling, as [randomCard]
 * returns a random card upon every access. Decks should not modify CardPacks.
 */
public interface Deck<T : Card> {

    /**
     * All of the [CardPack]s in this [Deck].
     */
    val cardPacks: Collection<CardPack<T>>
    /**
     * A [MutableList] of [Card]s. This should have copies of every card available in this deck's card packs.
     */
    val allCards: MutableList<T>
    /**
     * A random [Card] that is different upon every access. Accessing this card removes it from [allCards].
     */
    val randomCard: T

}
