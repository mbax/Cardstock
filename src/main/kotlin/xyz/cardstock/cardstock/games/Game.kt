/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.games

import com.google.common.collect.Lists
import org.kitteh.irc.client.library.element.Channel
import org.kitteh.irc.client.library.element.User
import xyz.cardstock.cardstock.Cardstock
import xyz.cardstock.cardstock.extensions.channel.antiPing
import xyz.cardstock.cardstock.interfaces.states.State
import xyz.cardstock.cardstock.players.Player
import java.util.Collections

/**
 * This class represents any game that [Cardstock] can facilitate.
 *
 * Games in Cardstock run on a state system. The game starts on the first state, which is provided in the constructor.
 * From there, if any call to [advanceState] is made, the next state, provided by [State.next], is made the current
 * state. After setting the state, the abstract method [processCurrentState] is called, allowing for the game to set up
 * anything necessary for the current state.
 *
 * Games may also have a state in which multiple [Round][xyz.cardstock.cardstock.games.rounds.Round]s are carried out,
 * in a similar state-based process.
 *
 * Upon reaching the terminal state, the game is considered over and should be handled as appropriate.
 * @param[PLAYER_TYPE] The type of [Player] that this game entertains
 * @constructor Creates a new Game.
 * @param[cardstock] The [Cardstock] facilitating this game.
 * @param[channel] The [Channel] that this game is taking place in.
 * @param[state] The starting state of this game. See [Game] to learn more about states.
 */
public abstract class Game<PLAYER_TYPE : Player>(public val cardstock: Cardstock, public val channel: Channel, state: State) {

    /**
     * The current [State] of this game.
     */
    public var state: State
        protected set
    /**
     * The modifiable list of [Player]s. This is meant to be used internally by the game in order to manage current
     * players.
     */
    protected val _players = Lists.newArrayList<PLAYER_TYPE>()
    /**
     * An unmodifiable list of [Player]s currently in this game. Use [getPlayer] to create Players and [removePlayer] to
     * remove Players.
     */
    public val players: List<PLAYER_TYPE>
        get() = Collections.unmodifiableList(this._players)

    init {
        this.$state = state
    }

    /**
     * Progresses the Game to its next [State], which is provided by [state]'s [State.next]. If this is the final state
     * for this game, this method has no effect.
     */
    public open fun advanceState() {
        this.$state = this.state.next ?: return
        this.processCurrentState()
    }

    /**
     * Sets up the Game for its current [State]. This is always called after [advanceState] changes the [state] to the
     * next state.
     */
    public abstract fun processCurrentState()

    /**
     * Sends [message] to the [Channel] after modifying it so that it will not ping any [User]s.
     */
    public open fun sendMessage(message: String) {
        this.channel.sendMessage(this.channel.antiPing(message))
    }

    /**
     * Gets the [Player] that corresponds to [user] that is currently playing the game. If there is no such player, one
     * of two possibilities will occur.
     * 1. If [create] is true, a new player will be created, added to the game, and returned.
     * 2. If [create] is false, `null` will be returned.
     */
    public abstract fun getPlayer(user: User, create: Boolean = true): PLAYER_TYPE?

    /**
     * Removes [player] from the list of current players.
     */
    public fun removePlayer(player: PLAYER_TYPE) {
        this._players.remove(player)
    }

    /**
     * Removes [user] from the list of current players. Calls [removePlayer(Player)] on the result of [getPlayer].
     * This will not create a [Player] if [user] does not correspond to a current player. In such a case, this method
     * will have no effect.
     */
    public open fun removePlayer(user: User) {
        val player = this.getPlayer(user, false) ?: return
        this.removePlayer(player)
    }

}
