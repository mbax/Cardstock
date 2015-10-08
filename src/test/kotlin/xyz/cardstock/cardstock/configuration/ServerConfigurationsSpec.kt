/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.configuration

import org.jetbrains.spek.api.shouldThrow
import xyz.cardstock.cardstock.MavenSpek
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class ServerConfigurationsSpec : MavenSpek() {
    override fun test() {
        given("a ServerConfigurations initialized with valid data") {
            val serverConfigurations = ServerConfigurations(File("src/test/resources/configuration.json"))
            on("accessing the servers") {
                val servers = serverConfigurations.servers
                it("should be unmodifiable") {
                    // We'll just hope that the rest are unsupported
                    shouldThrow(UnsupportedOperationException::class.java) {
                        (servers as MutableList<Server>).remove(0)
                    }
                }
                it("should contain two servers") {
                    assertEquals(2, servers.size())
                }
                it("should contain one server with the host \"irc.example.com\"") {
                    assertEquals(1, servers.filter { it.host == "irc.example.com" }.size())
                }
                it("should contain one server with the host \"irc.example.org\"") {
                    assertEquals(1, servers.filter { it.host == "irc.example.org" }.size())
                }
            }
            on("accessing the first server") {
                val server = serverConfigurations.servers.first()
                it("should not have a null channel property") {
                    assertNotNull(server.channels)
                }
                it("should have one channel") {
                    assertEquals(1, server.channels?.size())
                }
                it("should have one channel with the name \"#slash\"") {
                    assertEquals(1, server.channels?.filter { it == "#slash" }?.size())
                }
                it("should have a port of 6667") {
                    assertEquals(6667, server.port)
                }
                it("should have a false secure value") {
                    assertFalse(server.secure)
                }
                it("should have a nick of \"Test\"") {
                    assertEquals("Test", server.nickname)
                }
                it("should have a prefix of '!'") {
                    assertEquals('!', server.prefix)
                }
                it("should have a null user property") {
                    assertNull(server.user)
                }
                it("should have a null password property") {
                    assertNull(server.password)
                }
            }
            on("accessing the second server") {
                val server = serverConfigurations.servers.get(1)
                it("should not have a null channel property") {
                    assertNotNull(server.channels)
                }
                it("should have one channel") {
                    assertEquals(1, server.channels?.size())
                }
                it("should have one channel with the name \"#dog\"") {
                    assertEquals(1, server.channels?.filter { it == "#dog" }?.size())
                }
                it("should have a port of 6667") {
                    assertEquals(6667, server.port)
                }
                it("should have a false secure value") {
                    assertFalse(server.secure)
                }
                it("should have a nick of \"Test\"") {
                    assertEquals("Test", server.nickname)
                }
                it("should have a prefix of '!'") {
                    assertEquals('!', server.prefix)
                }
                it("should have a null user property") {
                    assertNull(server.user)
                }
                it("should have a null password property") {
                    assertNull(server.password)
                }
            }
        }
    }
}
