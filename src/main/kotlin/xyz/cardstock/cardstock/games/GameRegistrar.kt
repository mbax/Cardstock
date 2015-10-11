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

/**
 * The game registrar is where games that are being played are registered. It keeps track of ongoing games.
 * @param[C] The bot's type
 * @param[P] The type of player in games registered
 * @param[G] The type of games being registered
 * @constructor Constructs a new [GameRegistrar]
 * @param[mapper] Converts an instance of [C] and a [Channel] to an instance of [G].
 */
class GameRegistrar<C : Cardstock, P : Player, G : Game<P>>(val cardstock: C, val mapper: (C, Channel) -> G) {

    /**
     * The mutable map of Channels -> Games.
     */
    private val games = Maps.newHashMap<Channel, G>()

    /**
     * Gets all registered games as an unmodifiable set.
     */
    fun all(): Set<G> = Collections.unmodifiableSet(this.games.values().toSet())

    /**
     * Finds a game registered in [channel]. Returns `null` if none were found.
     */
    fun find(channel: Channel) = this.games[channel]

    /**
     * Creates a game in [channel] and registers it.
     */
    fun on(channel: Channel) = this.games.compute(channel) { k, v -> v ?: this.mapper(this.cardstock, channel) }

    /**
     * Removes the game registered in [channel].
     */
    fun end(channel: Channel) {
        this.games.remove(channel)
    }

    /**
     * Removes [game].
     */
    fun end(game: G) {
        this.games.values().remove(game)
    }

}
