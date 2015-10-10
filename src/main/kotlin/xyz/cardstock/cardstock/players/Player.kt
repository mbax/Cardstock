/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.players

import org.kitteh.irc.client.library.element.User

/**
 * A [User] that is part of a [Game][xyz.cardstock.cardstock.games.Game].
 */
interface Player {

    /**
     * The [User] that this Player represents.
     */
    val user: User

}
