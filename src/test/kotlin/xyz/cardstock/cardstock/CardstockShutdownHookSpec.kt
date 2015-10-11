/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock

import com.google.common.collect.Lists
import org.kitteh.irc.client.library.Client
import org.mockito.Matchers.anyString
import org.mockito.Matchers.eq
import org.mockito.Mockito.verify
import org.powermock.api.mockito.PowerMockito.doNothing
import org.powermock.api.mockito.PowerMockito.mock
import xyz.cardstock.cardstock.implementations.DummyCardstock
import kotlin.test.assertEquals

class CardstockShutdownHookSpec : MavenSpek() {

    private fun makeClient(): Client {
        val client = mock(Client::class.java)
        doNothing().`when`(client).sendRawLine(anyString())
        doNothing().`when`(client).shutdown(anyString())
        return client
    }

    override fun test() {
        given("the Cardstock shutdown hook") {
            val cardstock = DummyCardstock()
            val client = this@CardstockShutdownHookSpec.makeClient()
            cardstock.addClient(client)
            val shutdownHook = CardstockShutdownHook(cardstock)
            val results = Lists.newArrayList<String>()
            on("adding three beginning hooks") {
                shutdownHook.beginningHooks.add {
                    results.add("1B")
                }
                shutdownHook.beginningHooks.add {
                    throw Exception("Boo, I'm a scary exception!")
                }
                shutdownHook.beginningHooks.add {
                    results.add("2B")
                }
                it("should have three beginning hooks") {
                    assertEquals(3, shutdownHook.beginningHooks.size())
                }
            }
            on("adding two end hooks") {
                shutdownHook.endHooks.add {
                    results.add("1E")
                }
                shutdownHook.endHooks.add {
                    results.add("2E")
                }
                it("should have two end hooks") {
                    assertEquals(2, shutdownHook.endHooks.size())
                }
            }
            on("running") {
                shutdownHook.run()
                it("should leave all channels") {
                    verify(client).sendRawLine(eq("JOIN :0"))
                }
                it("should leave the server") {
                    verify(client).shutdown(anyString())
                }
                it("should run all hooks, catching exceptions") {
                    assertEquals(4, results.size())
                }
                it("should run all beginning hooks first, in order") {
                    assertEquals("1B", results[0])
                    assertEquals("2B", results[1])
                }
                it("should run all end hooks last, in order") {
                    assertEquals("1E", results[2])
                    assertEquals("2E", results[3])
                }
            }
        }
    }
}
