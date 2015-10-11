/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.implementations.games.rounds

import xyz.cardstock.cardstock.games.Game
import xyz.cardstock.cardstock.games.rounds.Round
import xyz.cardstock.cardstock.implementations.players.DummyPlayer
import xyz.cardstock.cardstock.interfaces.states.State

internal class DummyRound(override val game: Game<DummyPlayer>, override val number: Int) : Round<DummyPlayer> {
    override var state: State
        get() = throw UnsupportedOperationException()
        set(value) {
        }
    override val stateListeners: MutableList<(State) -> Unit>
        get() = throw UnsupportedOperationException()
}
