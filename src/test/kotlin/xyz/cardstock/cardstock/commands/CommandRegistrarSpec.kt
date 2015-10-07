/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.commands

import org.jetbrains.spek.api.shouldThrow
import org.kitteh.irc.client.library.element.User
import org.kitteh.irc.client.library.event.helper.ActorEvent
import xyz.cardstock.cardstock.MavenSpek
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CommandRegistrarSpec : MavenSpek() {

    @Command(
        name = "test",
        aliases = arrayOf(
            "spec"
        )
    )
    private class TestCommand : BaseCommand() {
        override fun run(event: ActorEvent<User>, callInfo: CallInfo, arguments: List<String>) {
            throw UnsupportedOperationException()
        }
    }

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
                    assertEquals(null, commandRegistrar[command.name])
                }
                it("should return null if queried by alias") {
                    assertEquals(null, commandRegistrar[command.aliases[0]])
                }
            }
        }
    }

}
