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
import xyz.cardstock.cardstock.implementations.commands.CompleteTestCommand
import xyz.cardstock.cardstock.implementations.commands.NoOpDummyCommand
import xyz.cardstock.cardstock.implementations.commands.TestCommand
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BaseCommandSpec : MavenSpek() {

    private class UnannotatedTestCommand : BaseCommand() {
        override fun run(event: ActorEvent<User>, callInfo: CallInfo, arguments: List<String>) {
            throw UnsupportedOperationException()
        }
    }

    override fun test() {
        given("an unannotated test command") {
            val command = UnannotatedTestCommand()
            on("accessing its name") {
                it("should throw an NPE") {
                    shouldThrow(NullPointerException::class.java) { command.name }
                }
            }
            on("accessing its aliases") {
                it("should throw an NPE") {
                    shouldThrow(NullPointerException::class.java) { command.aliases }
                }
            }
            on("accessing its description") {
                it("should throw an NPE") {
                    shouldThrow(NullPointerException::class.java) { command.description }
                }
            }
            on("accessing its usage") {
                it("should throw an NPE") {
                    shouldThrow(NullPointerException::class.java) { command.usage }
                }
            }
            on("accessing its CommandType") {
                it("should throw an NPE") {
                    shouldThrow(NullPointerException::class.java) { command.commandType }
                }
            }
        }
        given("a partially-annotated test command") {
            val command = TestCommand()
            on("accessing its name") {
                val name = command.name
                it("should be \"test\"") {
                    assertEquals("test", name)
                }
            }
            on("accessing its aliases") {
                val aliases = command.aliases
                it("should only have one alias") {
                    assertEquals(1, aliases.size)
                }
                it("should have an alias called \"spec\"") {
                    assertEquals("spec", aliases[0])
                }
            }
            on("accessing its description") {
                val description = command.description
                it("should be an empty string") {
                    assertTrue(description.isEmpty())
                }
            }
            on("accessing its usage") {
                val usage = command.usage
                it("should be \"<command>\"") {
                    assertEquals("<command>", usage)
                }
            }
            on("accessing it CommandType") {
                val commandType = command.commandType
                it("should be BOTH") {
                    assertEquals(BaseCommand.CommandType.BOTH, commandType)
                }
            }
        }
        given("a completely-annotated test command") {
            val command = CompleteTestCommand()
            on("accessing its name") {
                val name = command.name
                it("should be \"completetest\"") {
                    assertEquals("completetest", name)
                }
            }
            on("accessing its aliases") {
                val aliases = command.aliases
                it("should only have one alias") {
                    assertEquals(1, aliases.size)
                }
                it("should have an alias called \"testcomplete\"") {
                    assertEquals("testcomplete", aliases[0])
                }
            }
            on("accessing its description") {
                val description = command.description
                it("should be \"A test command.\"") {
                    assertEquals("A test command.", description)
                }
            }
            on("accessing its usage") {
                val usage = command.usage
                it("should be \"<command> [something]\"") {
                    assertEquals("<command> [something]", usage)
                }
            }
            on("accessing it CommandType") {
                val commandType = command.commandType
                it("should be CHANNEL") {
                    assertEquals(BaseCommand.CommandType.CHANNEL, commandType)
                }
            }
        }
        given("a command") {
            val command = NoOpDummyCommand("noop")
            on("comparison with a command with the same metadata") {
                val commandTwo = NoOpDummyCommand("noop")
                val equals = command == commandTwo
                it("should equal") {
                    assertTrue(equals)
                }
            }
            on("comparison with a command with completely different metadata") {
                val commandTwo = NoOpDummyCommand("derp", arrayOf("herp"), description = "Herp derp", usage = "<command> herp", commandType = BaseCommand.CommandType.CHANNEL)
                val equals = command == commandTwo
                it("should not equal") {
                    assertFalse(equals)
                }
            }
            on("comparison with a command with a different name") {
                val commandTwo = NoOpDummyCommand("derp")
                val equals = command == commandTwo
                it("should not equal") {
                    assertFalse(equals)
                }
            }
            on("comparison with a command with different aliases") {
                val commandTwo = NoOpDummyCommand("noop", arrayOf("herp"))
                val equals = command == commandTwo
                it("should not equal") {
                    assertFalse(equals)
                }
            }
            on("comparison with a command with a different description") {
                val commandTwo = NoOpDummyCommand("noop", description = "Herp derp")
                val equals = command == commandTwo
                it("should not equal") {
                    assertFalse(equals)
                }
            }
            on("comparison with a command with a different usage") {
                val commandTwo = NoOpDummyCommand("noop", usage = "<command> herp")
                val equals = command == commandTwo
                it("should not equal") {
                    assertFalse(equals)
                }
            }
            on("comparison with a command with a different command type") {
                val commandTwo = NoOpDummyCommand("noop", commandType = BaseCommand.CommandType.CHANNEL)
                val equals = command == commandTwo
                it("should not equal") {
                    assertFalse(equals)
                }
            }
            on("comparison an unrelated object") {
                val obj = object {}
                val equals = command.equals(obj)
                it("should not equal") {
                    assertFalse(equals)
                }
            }
            on("comparison with null") {
                val equals = command.equals(null)
                it("should not equal") {
                    assertFalse(equals)
                }
            }
        }
    }
}
