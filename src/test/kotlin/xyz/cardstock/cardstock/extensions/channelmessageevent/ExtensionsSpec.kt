/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.extensions.channelmessageevent

import com.google.common.collect.Lists
import org.jetbrains.spek.api.Spek
import org.kitteh.irc.client.library.Client
import org.kitteh.irc.client.library.element.Channel
import org.kitteh.irc.client.library.element.User
import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent
import org.mockito.Matchers.anyString
import org.mockito.Matchers.eq
import org.mockito.Mockito.verify
import org.powermock.api.mockito.PowerMockito.*

class ExtensionsSpec : Spek({

    fun makeUser(nick: String): User {
        val user = mock(User::class.java)
        `when`(user.nick).thenReturn(nick)
        return user
    }

    fun makeChannelMessageEvent(channel: Channel, user: User): ChannelMessageEvent {
        val client = mock(Client::class.java)
        `when`(user.client).thenReturn(client)
        `when`(channel.client).thenReturn(client)
        return ChannelMessageEvent(client, Lists.newArrayList(), user, channel, "")
    }

    fun makeChannel(): Channel {
        val channel = mock(Channel::class.java)
        doNothing().`when`(channel).sendMessage(anyString())
        return channel
    }

    given("a mock ChannelMessageEvent") {
        val user = makeUser("Joe")
        val channel = makeChannel()
        val event = makeChannelMessageEvent(channel, user)
        on("respond") {
            val message = "Hello!"
            event.respond(message)
            it("should ping the user") {
                verify(channel).sendMessage(eq("${user.nick}: $message"))
            }
        }
    }
})
