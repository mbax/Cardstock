/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.cards.packs

import xyz.cardstock.cardstock.cards.Card

/**
 * A pack of cards. These packs should be mixed into a [Deck][xyz.cardstock.cardstock.cards.decks.Deck] for playing.
 */
public interface CardPack<T : Card> {

    /**
     * The name of this CardPack, which should be unique amongst all other loaded packs.
     */
    val name: String
    /**
     * The cards contained in this pack.
     */
    val cards: List<T>

}
