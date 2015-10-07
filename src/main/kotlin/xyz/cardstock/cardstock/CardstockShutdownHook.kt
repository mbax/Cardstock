/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock

import com.google.common.collect.Lists

public class CardstockShutdownHook(val cardstock: Cardstock) : Runnable {

    val beginningHooks = Lists.newArrayList<(Cardstock) -> Unit>()
    val endHooks = Lists.newArrayList<(Cardstock) -> Unit>()
    private val runHook = { hook: (Cardstock) -> Unit ->
        try {
            hook(this.cardstock)
        } catch (ex: Throwable) {
            this.cardstock.logger.severe("A hook encountered an error in the shutdown hook:")
            ex.printStackTrace()
        }
    }

    override fun run() {
        this.beginningHooks.forEach(this.runHook)
        val numberOfClients = this.cardstock.clients.size()
        val sleepTime = 1000L + (500L * Math.max(0, numberOfClients - 1))
        // Leave all channels. More effective than a QUIT for ZNC
        this.cardstock.clients.forEach { it.sendRawLine("JOIN :0") }
        // Wait for clients
        Thread.sleep(sleepTime)
        // Still quit for good measure
        this.cardstock.clients.forEach { it.shutdown("Too many weird slashfics") }
        // Wait for clients
        Thread.sleep(sleepTime)
        this.endHooks.forEach(this.runHook)
    }
}
