/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.implementations.players

import org.kitteh.irc.client.library.element.User
import xyz.cardstock.cardstock.players.Player

internal class DummyPlayer(override val user: User) : Player
