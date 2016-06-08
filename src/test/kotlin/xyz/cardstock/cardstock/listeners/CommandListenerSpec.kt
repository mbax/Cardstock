/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.listeners

import com.google.common.collect.Lists
import org.jetbrains.spek.api.Spek
import org.kitteh.irc.client.library.Client
import org.kitteh.irc.client.library.element.Channel
import org.kitteh.irc.client.library.element.ServerMessage
import org.kitteh.irc.client.library.element.User
import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent
import org.kitteh.irc.client.library.event.user.PrivateMessageEvent
import org.powermock.api.mockito.PowerMockito.`when`
import org.powermock.api.mockito.PowerMockito.mock
import org.powermock.core.classloader.annotations.PrepareForTest
import xyz.cardstock.cardstock.commands.BaseCommand
import xyz.cardstock.cardstock.configuration.Server
import xyz.cardstock.cardstock.implementations.DummyCardstock
import xyz.cardstock.cardstock.implementations.commands.TestCommand
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@PrepareForTest(BaseCommand::class)
class CommandListenerSpec : Spek({

    fun makeChannelMessageEvent(message: String, client: Client): ChannelMessageEvent {
        val originalMessages = Lists.newArrayList<ServerMessage>()
        val sender = mock(User::class.java)
        `when`(sender.client).thenReturn(client)
        val channel = mock(Channel::class.java)
        `when`(channel.client).thenReturn(client)
        return ChannelMessageEvent(client, originalMessages, sender, channel, message)
    }

    fun makePrivateMessageEvent(message: String, client: Client): PrivateMessageEvent {
        val originalMessages = Lists.newArrayList<ServerMessage>()
        val sender = mock(User::class.java)
        `when`(sender.client).thenReturn(client)
        return PrivateMessageEvent(client, originalMessages, sender, message)
    }

    // Create dummy Cardstock
    val cardstock = DummyCardstock()
    // Create mocked Client
    val client = mock(Client::class.java)
    // Create the test command
    val command = TestCommand(false)
    // Register the test command
    cardstock.commandRegistrar.registerCommand(command)
    // Create the event to give to the CommandListener
    val event = makeChannelMessageEvent("!test", client)
    // Create the private event
    val privateEvent = makePrivateMessageEvent("!test", client)
    // Add fake client -> server mappings for the CommandListener to use
    cardstock.addClientServerMapping(event.client, Server("", 0, false, "", '!'))

    given("a CommandListener") {
        val commandListener = CommandListener(cardstock)
        on("accessing the Cardstock instance") {
            it("should be the same as the one provided to it") {
                assertTrue(cardstock === commandListener.cardstock)
            }
        }
        on("receiving a command") {
            commandListener.onChannelCommand(event)
            it("should execute the command") {
                assertTrue(command.wasRun)
                command.wasRun = false
            }
        }
        on("receiving a command that throws an exception") {
            it("should catch the exception") {
                command.throwException = true
                commandListener.onChannelCommand(event)
                // The exception is thrown after this is set to true, so ensure it is set
                assertTrue(command.wasRun)
                command.wasRun = false
            }
        }
        on("receiving a private command") {
            it("should execute the command") {
                command.throwException = false
                commandListener.onPrivateCommand(privateEvent)
                assertTrue(command.wasRun)
                command.wasRun = false
            }
        }
        on("receiving a private command that throws an exception") {
            it("should catch the exception") {
                command.throwException = true
                commandListener.onPrivateCommand(privateEvent)
                // The exception is thrown after this is set to true, so ensure it is set
                assertTrue(command.wasRun)
                command.wasRun = false
            }
        }
        on("receiving an empty channel message") {
            commandListener.onChannelCommand(makeChannelMessageEvent("", client))
            it("should do nothing") {
                assertFalse(command.wasRun)
            }
        }
        on("receiving an empty private message") {
            commandListener.onPrivateCommand(makePrivateMessageEvent("", client))
            it("should do nothing") {
                assertFalse(command.wasRun)
            }
        }
        on("receiving a command that doesn't start with the prefix") {
            commandListener.onChannelCommand(makeChannelMessageEvent("Hello", client))
            it("should do nothing") {
                assertFalse(command.wasRun)
            }
        }
        on("receiving a command that doesn't exist") {
            commandListener.onChannelCommand(makeChannelMessageEvent("!help", client))
            it("should do nothing") {
                assertFalse(command.wasRun)
            }
        }
    }
})
