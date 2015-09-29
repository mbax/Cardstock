/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.games.rounds

import xyz.cardstock.cardstock.games.Game
import xyz.cardstock.cardstock.players.Player

/**
 * A round in a [Game]. Rounds take place during particular [State][xyz.cardstock.cardstock.games.State]s of [Game]s.
 * Each round has a [number], which should be different for every round, most likely increasing by one per round.
 *
 * The [Game] that this round is taking place in is [game].
 */
public interface Round<PLAYER_TYPE : Player> {

    /**
     * The [Game] that this Round is taking place in.
     */
    val game: Game<PLAYER_TYPE>
    /**
     * The number of this Round, which should be unique in the current [Game].
     */
    val number: Int

}
