/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.extensions.channel

import org.jetbrains.spek.api.Spek
import org.kitteh.irc.client.library.Client
import org.kitteh.irc.client.library.command.ModeCommand
import org.kitteh.irc.client.library.element.Channel
import org.kitteh.irc.client.library.element.ChannelUserMode
import org.kitteh.irc.client.library.element.User
import org.kitteh.irc.client.library.feature.ServerInfo
import org.mockito.Matchers.*
import org.mockito.Mockito.verify
import org.powermock.api.mockito.PowerMockito.*
import xyz.cardstock.cardstock.extensions.string.get
import java.util.Optional
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ExtensionsSpec : Spek({

    fun makeChannelUserMode(char: Char): ChannelUserMode {
        val channelUserMode = mock(ChannelUserMode::class.java)
        `when`(channelUserMode.char).thenReturn(char)
        return channelUserMode
    }

    fun makeServerInfo(): ServerInfo {
        val serverInfo = mock(ServerInfo::class.java)
        `when`(serverInfo.getChannelUserMode(anyChar())).thenAnswer {
            Optional.of(makeChannelUserMode(it.arguments[0] as Char))
        }
        return serverInfo
    }

    fun makeClient(): Client {
        val client = mock(Client::class.java)
        val serverInfo = makeServerInfo()
        `when`(client.serverInfo).thenReturn(serverInfo)
        doNothing().`when`(client).sendRawLine(anyString())
        return client
    }

    fun makeModeCommand(): ModeCommand {
        val modeCommand = mock(ModeCommand::class.java)
        `when`(modeCommand.add(anyBoolean(), any(ChannelUserMode::class.java), any(User::class.java))).thenReturn(null)
        return modeCommand
    }

    fun makeChannel(client: Client): Channel {
        val channel = mock(Channel::class.java)
        `when`(channel.client).thenReturn(client)
        val modeCommand = makeModeCommand()
        `when`(channel.newModeCommand()).thenReturn(modeCommand)
        val name = "#test"
        `when`(channel.name).thenReturn(name)
        `when`(channel.messagingName).thenReturn(name)
        return channel
    }

    fun makeUser(): User {
        val user = mock(User::class.java)
        val name = "test"
        `when`(user.nick).thenReturn(name)
        `when`(user.messagingName).thenReturn(name)
        return user
    }

    given("a mock Channel") {
        val client = makeClient()
        val channel = makeChannel(client)
        on("userMode") {
            val user = makeUser()
            channel.userMode(UserModeData(true, 'o', user))
            it("should have sent a mode command") {
                val modeCommand = channel.newModeCommand()
                verify(modeCommand).add(eq(true), any(ChannelUserMode::class.java), eq(user))
                verify(modeCommand).execute()
            }
        }
        on("antiPing") {
            val nicknames = listOf("Joe", "Bob", "D")
            `when`(channel.nicknames).thenReturn(nicknames)
            val message = "I like Joe and Bob! Not D, though. Name's too short."
            val result = channel.antiPing(message)
            it("should not contain any nicknames, if they're longer than one character") {
                assertFalse(nicknames.map { it.length != 1 && result.contains(it) }.reduce({ b1, b2 -> b1 || b2 }))
            }
            it("should contain nicknames with zero-width spaces, if they're longer than one character") {
                assertTrue(nicknames.map { if (it.length == 1) null else it[0] + "\u200b" + it[1, null] }.filterNotNull().map { result.contains(it) }.reduce { b1, b2 -> b1 && b2 })
            }
        }
    }
})
