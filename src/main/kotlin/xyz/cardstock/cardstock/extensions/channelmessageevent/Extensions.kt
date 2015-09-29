/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.extensions.channelmessageevent

import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent

public fun ChannelMessageEvent.respond(message: String) {
    this.channel.sendMessage("${this.actor.nick}: $message")
}
