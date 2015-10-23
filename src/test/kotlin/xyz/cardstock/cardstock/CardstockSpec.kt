/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock

import xyz.cardstock.cardstock.implementations.DummyCardstock
import xyz.cardstock.cardstock.implementations.StartableDummyCardstock
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.util.IdentityHashMap
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CardstockSpec : MavenSpek() {
    override fun test() {
        given("a Cardstock instance") {
            val cardstock = DummyCardstock()
            on("setting up and invoking the logger") {
                // Prepare to catch the output
                val output = ByteArrayOutputStream()
                val printStream = PrintStream(output)
                val oldErr = System.err
                System.setErr(printStream)
                // Set up the logger
                cardstock.doSetUpLogger()
                it("should output in the expected format") {
                    val string = "Testing 123"
                    cardstock.logger.info(string)
                    assertEquals("[INFO] $string\n", output.toString())
                }
                // Restore output
                System.setErr(oldErr)
            }
        }
        given("a startable Cardstock instance") {
            val cardstock = StartableDummyCardstock(arrayOf("-c", "src/test/resources/configuration.json"))
            on("starting the bot") {
                cardstock.start()
                it("should start three clients") {
                    assertEquals(3, cardstock.clients.size)
                }
                it("should map the clients to their Server objects") {
                    assertEquals(3, cardstock.clientServerMap.size)
                }
                it("should register the shutdown hook") {
                    @Suppress("UNCHECKED_CAST")
                    val hooks = Class.forName("java.lang.ApplicationShutdownHooks").getDeclaredField("hooks").apply { this.isAccessible = true }.get(null) as IdentityHashMap<Thread, Thread>
                    // One is registered by the JVM
                    assertTrue(hooks.size > 1)
                }
                it("should stop") {
                    cardstock.clients.forEach { it.shutdown() }
                }
            }
        }
    }
}
