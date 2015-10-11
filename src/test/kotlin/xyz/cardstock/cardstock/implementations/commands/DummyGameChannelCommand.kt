/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.implementations.commands

import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent
import xyz.cardstock.cardstock.commands.CallInfo
import xyz.cardstock.cardstock.commands.GameChannelCommand
import xyz.cardstock.cardstock.implementations.DummyCardstock
import xyz.cardstock.cardstock.implementations.games.DummyGame
import xyz.cardstock.cardstock.implementations.players.DummyPlayer

class DummyGameChannelCommand(cardstock: DummyCardstock) : GameChannelCommand<DummyPlayer, DummyGame>(cardstock, { cardstock.gameRegistrar.find(it) }) {
    override fun run(event: ChannelMessageEvent, callInfo: CallInfo, game: DummyGame, player: DummyPlayer, arguments: List<String>) {
        throw TestCommandException()
    }
}
