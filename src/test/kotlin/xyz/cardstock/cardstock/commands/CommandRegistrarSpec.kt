/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.commands

import org.jetbrains.spek.api.shouldThrow
import xyz.cardstock.cardstock.MavenSpek
import xyz.cardstock.cardstock.implementations.commands.NoOpDummyCommand
import xyz.cardstock.cardstock.implementations.commands.TestCommand
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class CommandRegistrarSpec : MavenSpek() {

    override fun test() {
        given("a standard command registrar") {
            val commandRegistrar = CommandRegistrar()
            val command = TestCommand()
            on("registering a command") {
                commandRegistrar.registerCommand(command)
                it("should have one command") {
                    assertEquals(1, commandRegistrar.all().size())
                }
                it("should return the same command if queried by name") {
                    assertTrue(command === commandRegistrar[command.name])
                }
                it("should return the same command if queried by alias") {
                    assertTrue(command === commandRegistrar[command.aliases[0]])
                }
            }
            on("registering the same command again") {
                it("should throw an IllegalStateException") {
                    shouldThrow(IllegalStateException::class.java) {
                        commandRegistrar.registerCommand(command)
                    }
                }
            }
            on("registering a command with a clashing alias") {
                it("should throw an IllegalStateException") {
                    shouldThrow(IllegalStateException::class.java) {
                        commandRegistrar.registerCommand(NoOpDummyCommand("something", arrayOf("spec")))
                    }
                }
            }

        }
        // TODO: Merge this with the above `given`. This cannot be done because Spek is broken. It runs all `on` blocks
        //       at once, then it runs all `it` blocks. This breaks functionality involving state.
        given("a command registrar with one command") {
            val commandRegistrar = CommandRegistrar()
            val command = TestCommand()
            commandRegistrar.registerCommand(command)
            on("unregistering the same command") {
                commandRegistrar.unregisterCommand(command)
                it("should have zero commands") {
                    assertEquals(0, commandRegistrar.all().size())
                }
                it("should return null if queried by name") {
                    assertNull(commandRegistrar[command.name])
                }
                it("should return null if queried by alias") {
                    assertNull(commandRegistrar[command.aliases[0]])
                    assertNull(commandRegistrar.getCommandByAlias(command.aliases[0]))
                }
            }
        }
    }

}
