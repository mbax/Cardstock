/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.configuration

import com.google.common.jimfs.Jimfs
import org.jetbrains.spek.api.shouldThrow
import xyz.cardstock.cardstock.MavenSpek
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class ConfigurationSpec : MavenSpek() {

    private fun makePath(path: String, contents: String): Path {
        val fileSystem = Jimfs.newFileSystem(com.google.common.jimfs.Configuration.osX())
        val filePath = fileSystem.getPath(path)
        Files.write(filePath, listOf(contents), Charsets.UTF_8)
        return filePath
    }

    override fun test() {
        given("a Configuration initialized with valid data") {
            val configuration = Configuration(File("src/test/resources/configuration.json").toPath())
            on("accessing the servers") {
                val servers = configuration.servers
                it("should be unmodifiable") {
                    // We'll just hope that the rest are unsupported
                    shouldThrow(UnsupportedOperationException::class.java) {
                        (servers as MutableList<Server>).remove(0)
                    }
                }
                it("should contain three servers") {
                    assertEquals(3, servers.size())
                }
                it("should contain one server with the host \"irc.example.com\"") {
                    assertEquals(1, servers.filter { it.host == "irc.example.com" }.size())
                }
                it("should contain one server with the host \"irc.example.org\"") {
                    assertEquals(1, servers.filter { it.host == "irc.example.org" }.size())
                }
                it("should contain one server with the host \"irc.example.net\"") {
                    assertEquals(1, servers.filter { it.host == "irc.example.net" }.size())
                }
            }
            on("accessing the first server") {
                val server = configuration.servers.first()
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
                it("should have a null real name property") {
                    assertNull(server.realName)
                }
                it("should have a null password property") {
                    assertNull(server.password)
                }
            }
            on("accessing the second server") {
                val server = configuration.servers.get(1)
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
                it("should have a null real name property") {
                    assertNull(server.realName)
                }
                it("should have a null password property") {
                    assertNull(server.password)
                }
            }
            on("accessing the third server") {
                val server = configuration.servers[2]
                it("should have a user property of \"TestMan\"") {
                    assertEquals("TestMan", server.user)
                }
                it("should have a password property of \"wow\"") {
                    assertEquals("wow", server.password)
                }
            }
        }
        given("a Configuration initialized with invalid data") {
            on("construction") {
                it("should throw an IllegalStateException") {
                    shouldThrow(IllegalStateException::class.java) {
                        Configuration(File("src/test/resources/bad_configuration.json").toPath())
                    }
                }
            }
        }
        given("a Configuration initialized with even more invalid data") {
            val data = """{"servers": [[]]}"""
            on("construction") {
                it("should throw an IllegalStateException") {
                    shouldThrow(IllegalStateException::class.java) {
                        Configuration(this@ConfigurationSpec.makePath("herp.json", data))
                    }
                }
            }
        }
        given("a Configuration initialized with an empty JSON object") {
            on("construction") {
                it("should throw an IllegalStateException") {
                    shouldThrow(IllegalStateException::class.java) {
                        Configuration(this@ConfigurationSpec.makePath("herp.json", "{}"))
                    }
                }
            }
        }
        given("a Configuration initialized with no servers") {
            val data = """{"servers":[]}"""
            on("construction") {
                it("should throw an IllegalStateException") {
                    shouldThrow(IllegalStateException::class.java) {
                        Configuration(this@ConfigurationSpec.makePath("herp.json", data))
                    }
                }
            }
        }
    }
}
