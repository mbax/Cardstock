/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.extensions.channelmessageevent

import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent

/**
 * Sends a message to the channel in this event, starting with the actor's nick, a colon, and a space.
 */
public fun ChannelMessageEvent.respond(message: String) {
    this.channel.sendMessage("${this.actor.nick}: $message")
}
