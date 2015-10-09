/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock

import com.google.common.collect.Lists

/**
 * The shutdown hook that Cardstock uses to clean up before the JVM exits. May have hooks registered to be executed
 * prior to Cardstock logic and after.
 */
class CardstockShutdownHook(val cardstock: Cardstock) : Runnable {

    /**
     * A mutable list of hooks to be run before Cardstock does anything in the shutdown hook.
     */
    val beginningHooks = Lists.newArrayList<(Cardstock) -> Unit>()
    /**
     * A mutable list of hooks to be run after Cardstock's logic in the shutdown hook.
     */
    val endHooks = Lists.newArrayList<(Cardstock) -> Unit>()
    /**
     * Safe way to run the registered hooks and make sure nothing breaks.
     */
    private val runHook = { hook: (Cardstock) -> Unit ->
        try {
            hook(this.cardstock)
        } catch (ex: Throwable) {
            this.cardstock.logger.severe("A hook encountered an error in the shutdown hook:")
            ex.printStackTrace()
        }
    }

    /**
     * Executes the [beginningHooks], Cardstock logic, then the [endHooks].
     */
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
