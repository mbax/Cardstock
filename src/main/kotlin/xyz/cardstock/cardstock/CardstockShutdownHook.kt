/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock

import xyz.cardstock.cardstock.games.Game

public class CardstockShutdownHook(val cardstock: Cardstock) : Runnable {
    override fun run() {
        // Stop all games.
        Game.all().forEach { it.stop(Game.GameEndCause.SHUTDOWN) }
        // Wait one second
        Thread.sleep(1000L)
        // Leave all channels. More effective than a QUIT for ZNC
        this.cardstock.client.sendRawLine("JOIN :0")
        // Wait one second
        Thread.sleep(1000L)
        // Still quit for good measure
        this.cardstock.client.shutdown("Too many weird slashfics")
        // Wait one second
        Thread.sleep(1000L)
    }
}
