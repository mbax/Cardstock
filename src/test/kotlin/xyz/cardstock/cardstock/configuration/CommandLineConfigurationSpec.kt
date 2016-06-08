/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.configuration

import org.jetbrains.spek.api.Spek
import xyz.cardstock.cardstock.implementations.DummyCardstock
import xyz.cardstock.cardstock.implementations.ExitSecurityManager
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class CommandLineConfigurationSpec : Spek({

    val dummyCardstock = DummyCardstock()

    given("a Configuration constructed with only required arguments") {
        val args = "-c src/test/resources/configuration.json".split(" ").toTypedArray()
        val configuration = CommandLineConfiguration(args, dummyCardstock)
        on("accessing configurationFile") {
            val configurationFile = configuration.configurationFile
            it("should be the same path as specified in the args") {
                assertEquals(args[1], configurationFile.path)
            }
        }
        on("accessing configuration") {
            val conf = configuration.configuration
            it("should have servers") {
                assertTrue(conf.servers.size > 0)
            }
        }
    }
    given("a Configuration with no arguments") {
        on("init") {
            it("should exit with status 1") {
                System.setSecurityManager(ExitSecurityManager())
                val exception = assertFailsWith(ExitSecurityManager.ExitException::class) {
                    CommandLineConfiguration(arrayOf(), dummyCardstock)
                }
                System.setSecurityManager(null)
                assertEquals(1, exception.status)
            }
        }
    }
})
