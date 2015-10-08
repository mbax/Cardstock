/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.configuration

import org.jetbrains.spek.api.shouldThrow
import xyz.cardstock.cardstock.MavenSpek
import xyz.cardstock.cardstock.implementations.DummyCardstock
import xyz.cardstock.cardstock.implementations.ExitSecurityManager
import kotlin.test.assertEquals

class ConfigurationSpec : MavenSpek() {

    val dummyCardstock = DummyCardstock()

    override fun test() {
        given("a Configuration constructed with only required arguments") {
            val args = "-c src/test/resources/configuration.json -C src/test/resources/cards.json".split(" ").toTypedArray()
            val configuration = Configuration(args, this@ConfigurationSpec.dummyCardstock)
            on("accessing configurationFile") {
                val configurationFile = configuration.configurationFile
                it("should be the same path as specified in the args") {
                    assertEquals(args[1], configurationFile.path)
                }
            }
            on("accessing cardFiles") {
                val cardFiles = configuration.cardFiles
                it("should have one value") {
                    assertEquals(1, cardFiles.size())
                }
                it("should have one value that is the same as specified in the args") {
                    assertEquals(1, cardFiles.filter { it == args[3] }.size())
                }
            }
            on("accessing prefix") {
                val prefix = configuration.prefix
                it("should be the default of '!'") {
                    assertEquals('!', prefix)
                }
            }
            on("accessing nick") {
                val nick = configuration.nick
                it("should be the default of \"CardstockBot\"") {
                    assertEquals("CardstockBot", nick)
                }
            }
        }
        given("a Configuration with no arguments") {
            on("init") {
                it("should exit with status 1") {
                    System.setSecurityManager(ExitSecurityManager())
                    val exception = shouldThrow(ExitSecurityManager.ExitException::class.java) {
                        Configuration(arrayOf(), this@ConfigurationSpec.dummyCardstock)
                    }
                    System.setSecurityManager(null)
                    assertEquals(1, exception.status)
                }
            }
        }
        given("a Configuration with all arguments") {
            val args = "-c src/test/resources/configuration.json -C src/test/resources/cards.json -z $ -n TestBot".split(" ").toTypedArray()
            val configuration = Configuration(args, this@ConfigurationSpec.dummyCardstock)
            on("accessing configurationFile") {
                val configurationFile = configuration.configurationFile
                it("should be the same path as specified in the args") {
                    assertEquals(args[1], configurationFile.path)
                }
            }
            on("accessing cardFiles") {
                val cardFiles = configuration.cardFiles
                it("should have one value") {
                    assertEquals(1, cardFiles.size())
                }
                it("should have one value that is the same as specified in the args") {
                    assertEquals(1, cardFiles.filter { it == args[3] }.size())
                }
            }
            on("accessing prefix") {
                val prefix = configuration.prefix
                it("should be the symbol specified in the args") {
                    assertEquals(args[5][0], prefix)
                }
            }
            on("accessing nick") {
                val nick = configuration.nick
                it("should be the nick specified in the args") {
                    assertEquals(args[7], nick)
                }
            }
        }
    }
}
