/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.listeners

import com.google.common.collect.Lists
import org.kitteh.irc.client.library.Client
import org.kitteh.irc.client.library.element.Channel
import org.kitteh.irc.client.library.element.ServerMessage
import org.kitteh.irc.client.library.element.User
import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent
import org.powermock.api.mockito.PowerMockito.`when`
import org.powermock.api.mockito.PowerMockito.mock
import org.powermock.core.classloader.annotations.PrepareForTest
import xyz.cardstock.cardstock.MavenSpek
import xyz.cardstock.cardstock.commands.BaseCommand
import xyz.cardstock.cardstock.configuration.Server
import xyz.cardstock.cardstock.implementations.DummyCardstock
import xyz.cardstock.cardstock.implementations.commands.TestCommand
import kotlin.test.assertTrue

@PrepareForTest(BaseCommand::class)
class CommandListenerSpec : MavenSpek() {

    private fun makeChannelMessageEvent(message: String): ChannelMessageEvent {
        val client = mock(Client::class.java)
        val originalMessages = Lists.newArrayList<ServerMessage>()
        val sender = mock(User::class.java)
        `when`(sender.client).thenReturn(client)
        val channel = mock(Channel::class.java)
        `when`(channel.client).thenReturn(client)
        return ChannelMessageEvent(client, originalMessages, sender, channel, message)
    }

    override fun test() {
        // Create dummy Cardstock
        val cardstock = DummyCardstock()
        // Create the test command
        val command = TestCommand(false)
        // Register the test command
        cardstock.commandRegistrar.registerCommand(command)
        // Create the event to give to the CommandListener
        val event = this@CommandListenerSpec.makeChannelMessageEvent("!test")
        // Add a fake client -> server mapping for the CommandListener to use
        cardstock.addClientServerMapping(event.client, Server("", 0, false, "", '!'))

        given("a CommandListener") {
            val commandListener = CommandListener(cardstock)
            on("receiving a command") {
                commandListener.onChannelCommand(event)
                it("should execute the command") {
                    assertTrue(command.wasRun)
                    command.wasRun = false
                }
            }
            on("receiving a command that throws an exception") {
                command.throwException = true
                it("should catch the exception") {
                    commandListener.onChannelCommand(event)
                    // The exception is thrown after this is set to true, so ensure it is set
                    assertTrue(command.wasRun)
                }
            }
        }
    }
}
