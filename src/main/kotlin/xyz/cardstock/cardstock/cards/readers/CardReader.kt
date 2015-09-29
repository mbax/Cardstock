/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.cards.readers

import xyz.cardstock.cardstock.cards.Card

/**
 * A CardReader reads cards from a source. The cards are available from [cards].
 * @param[T] The type of cards to be read.
 */
public interface CardReader<T : Card> {

    /**
     * The cards read from the source.
     */
    public val cards: List<T>

}
