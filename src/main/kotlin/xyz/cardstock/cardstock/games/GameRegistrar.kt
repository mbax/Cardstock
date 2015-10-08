/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.games

import com.google.common.collect.Maps
import org.kitteh.irc.client.library.element.Channel
import xyz.cardstock.cardstock.Cardstock
import xyz.cardstock.cardstock.players.Player
import java.util.Collections

// TODO: Test stuff
// TODO: KDoc
class GameRegistrar<C : Cardstock, P : Player, G : Game<P>>(val cardstock: C, val mapper: (C, Channel) -> G) {

    private val games = Maps.newHashMap<Channel, G>()

    fun all(): Set<G> = Collections.unmodifiableSet(this.games.values().toSet())

    fun find(channel: Channel) = this.games[channel]

    fun on(cardstock: C, channel: Channel) = this.games.compute(channel) { k, v -> v ?: this.mapper(this.cardstock, channel) }

    fun end(channel: Channel) {
        this.games.remove(channel)
    }

    fun end(game: G) {
        this.games.values().remove(game)
    }

}
