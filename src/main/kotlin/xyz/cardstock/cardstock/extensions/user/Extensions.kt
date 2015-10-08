/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.extensions.user

import org.kitteh.irc.client.library.element.User

/**
 * Sends a message to this user, starting with their nickname, a colon, and a space.
 */
fun User.respond(message: String) {
    this.sendMessage("${this.nick}: $message")
}
