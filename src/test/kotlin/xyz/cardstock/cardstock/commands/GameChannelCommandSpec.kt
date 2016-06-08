/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.commands

import com.google.common.collect.Lists
import org.jetbrains.spek.api.Spek
import org.kitteh.irc.client.library.Client
import org.kitteh.irc.client.library.element.Channel
import org.kitteh.irc.client.library.element.ServerMessage
import org.kitteh.irc.client.library.element.User
import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent
import org.kitteh.irc.client.library.event.user.PrivateMessageEvent
import org.mockito.Matchers.eq
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.powermock.api.mockito.PowerMockito.`when`
import org.powermock.api.mockito.PowerMockito.mock
import xyz.cardstock.cardstock.implementations.DummyCardstock
import xyz.cardstock.cardstock.implementations.commands.DummyGameChannelCommand
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class GameChannelCommandSpec : Spek({

    fun makeChannel(): Channel {
        val channel = mock(Channel::class.java)
        return channel
    }

    fun makeUser(): User {
        val sender = mock(User::class.java)
        return sender
    }

    fun makeChannelMessageEvent(message: String, channel: Channel, sender: User): ChannelMessageEvent {
        val client = mock(Client::class.java)
        val originalMessages = Lists.newArrayList<ServerMessage>()
        `when`(sender.client).thenReturn(client)
        `when`(channel.client).thenReturn(client)
        return ChannelMessageEvent(client, originalMessages, sender, channel, message)
    }

    fun makePrivateMessageEvent(message: String): PrivateMessageEvent {
        val client = mock(Client::class.java)
        val originalMessages = Lists.newArrayList<ServerMessage>()
        val sender = mock(User::class.java)
        `when`(sender.client).thenReturn(client)
        return PrivateMessageEvent(client, originalMessages, sender, message)
    }

    given("a DummyGameChannelCommand") {
        val cardstock = DummyCardstock()
        val command = DummyGameChannelCommand(cardstock)
        val user = makeUser()
        on("a channel message event from a channel with a game") {
            val channel = makeChannel()
            val game = cardstock.gameRegistrar.on(channel)
            game.getPlayer(user, true)
            val event = makeChannelMessageEvent("", channel, user)
            it("should run the command") {
                command.run(event, CallInfo("", CallInfo.UsageType.CHANNEL), listOf())
                assertTrue(command.wasRun)
                command.wasRun = false
            }
        }
        on("a channel message event from a channel with a game without the user") {
            val channel = makeChannel()
            cardstock.gameRegistrar.on(channel)
            val event = makeChannelMessageEvent("", channel, user)
            it("should not run the command") {
                command.run(event, CallInfo("", CallInfo.UsageType.CHANNEL), listOf())
                assertFalse(command.wasRun)
            }
            it("should send a message to the user") {
                verify(user).sendNotice(eq("You are not in a game."))
            }
        }
        on("a channel message event from a channel without a game") {
            val channel = makeChannel()
            val event = makeChannelMessageEvent("", channel, user)
            it("should not run the command") {
                command.run(event, CallInfo("", CallInfo.UsageType.CHANNEL), listOf())
                assertFalse(command.wasRun)
            }
            it("should send a message to the user") {
                verify(user, times(2)).sendNotice(eq("You are not in a game."))
            }
        }
        on("a private message event") {
            val event = makePrivateMessageEvent("")
            it("should not run the command") {
                command.run(event, CallInfo("", CallInfo.UsageType.PRIVATE), listOf())
                assertFalse(command.wasRun)
            }
        }
    }
})
