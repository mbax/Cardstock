/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.players

import xyz.cardstock.cardstock.cards.Card
import xyz.cardstock.cardstock.players.hands.Hand

/**
 * A [Player] with a [Hand] of [Card]s.
 */
public interface HandedPlayer<T : Card> : Player {

    /**
     * The hand of cards that the player currently has.
     */
    val hand: Hand<T>

}
