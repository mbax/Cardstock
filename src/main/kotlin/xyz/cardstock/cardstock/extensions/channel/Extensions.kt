/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.extensions.channel

import org.kitteh.irc.client.library.element.Channel
import org.kitteh.irc.client.library.element.User

/**
 * Reformats a message to ensure no User in IRC is pinged by this message.

 * @param message Message to reformat
 * *
 * @return Reformatted message
 */
fun Channel.antiPing(message: String): String {
    var newMessage = message
    for (nickname in this.nicknames) {
        if (nickname.length <= 1) continue
        newMessage = newMessage.replace(nickname, nickname.substring(0, 1) + "\u200b" + nickname.substring(1))
    }
    return newMessage
}

/**
 * Sets or unsets a mode (or multiple modes) on a user (or multiple users) in the channel.
 * @param[data] Mode data
 */
fun Channel.userMode(vararg data: UserModeData) {
    val modeCommand = this.commands().mode()
    for ((add, mode, user) in data) {
        modeCommand.add(add, this.client.serverInfo.getChannelUserMode(mode).get(), user)
    }
    modeCommand.execute()
}

/**
 * Data for a user mode to be changed.
 */
data class UserModeData(val add: Boolean, val mode: Char, val user: User)
