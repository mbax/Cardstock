/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.commands

import org.kitteh.irc.client.library.element.Channel
import org.kitteh.irc.client.library.element.User
import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent
import org.kitteh.irc.client.library.event.helper.ActorEvent
import xyz.cardstock.cardstock.Cardstock
import xyz.cardstock.cardstock.games.Game
import xyz.cardstock.cardstock.players.Player

/**
 * A command to be used during a game in a channel.
 */
abstract class GameChannelCommand<P : Player, G : Game<P>>(val cardstock: Cardstock, val mapper: (Channel) -> G?) : BaseCommand() {
    override fun run(event: ActorEvent<User>, callInfo: CallInfo, arguments: List<String>) {
        if (event !is ChannelMessageEvent) return
        val game = this.mapper(event.channel)
        val player = game?.getPlayer(event.actor, create = false)
        if (game == null || player == null) {
            event.actor.sendNotice("You are not in a game.")
            return
        }
        this.run(event, callInfo, game, player, arguments)
    }

    /**
     * Performs the action that this command is supposed to perform. Called every time that this command is used.
     */
    abstract fun run(event: ChannelMessageEvent, callInfo: CallInfo, game: G, player: P, arguments: List<String>)
}
