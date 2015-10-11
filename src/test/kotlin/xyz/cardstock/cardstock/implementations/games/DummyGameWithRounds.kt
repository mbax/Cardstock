/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.implementations.games

import org.kitteh.irc.client.library.element.Channel
import org.kitteh.irc.client.library.element.User
import xyz.cardstock.cardstock.Cardstock
import xyz.cardstock.cardstock.games.GameWithRounds
import xyz.cardstock.cardstock.implementations.players.DummyPlayer
import xyz.cardstock.cardstock.interfaces.states.State

internal class DummyGameWithRounds(cardstock: Cardstock, channel: Channel) : GameWithRounds<DummyPlayer>(cardstock, channel) {
    override fun getPlayer(user: User, create: Boolean): DummyPlayer? {
        val current = this._players.firstOrNull { it.user == user }
        if (current != null) {
            return current
        }
        if (!create) {
            return null
        }
        return DummyPlayer(user).apply { this@DummyGameWithRounds._players.add(this) }
    }

    override var state: State
        get() = throw UnsupportedOperationException()
        set(value) {
        }
    override val stateListeners: MutableList<(State) -> Unit>
        get() = throw UnsupportedOperationException()
}
