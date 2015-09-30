/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock

public class CardstockShutdownHook(val cardstock: Cardstock) : Runnable {
    override fun run() {
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
