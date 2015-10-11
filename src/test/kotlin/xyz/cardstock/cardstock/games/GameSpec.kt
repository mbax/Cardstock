/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.games

import org.kitteh.irc.client.library.element.Channel
import org.kitteh.irc.client.library.element.User
import org.mockito.Matchers.anyString
import org.mockito.Matchers.eq
import org.mockito.Mockito.verify
import org.powermock.api.mockito.PowerMockito.`when`
import org.powermock.api.mockito.PowerMockito.doNothing
import org.powermock.api.mockito.PowerMockito.mock
import xyz.cardstock.cardstock.MavenSpek
import xyz.cardstock.cardstock.extensions.channel.antiPing
import xyz.cardstock.cardstock.implementations.DummyCardstock
import xyz.cardstock.cardstock.implementations.games.DummyGame
import xyz.cardstock.cardstock.implementations.players.DummyPlayer
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class GameSpec : MavenSpek() {

    private fun makeChannel(nicknames: List<String>): Channel {
        val channel = mock(Channel::class.java)
        doNothing().`when`(channel).sendMessage(anyString())
        `when`(channel.nicknames).thenReturn(nicknames)
        return channel
    }

    private fun makeUser(nickname: String): User {
        val user = mock(User::class.java)
        `when`(user.nick).thenReturn(nickname)
        return user
    }

    override fun test() {
        given("a DummyGame") {
            val cardstock = DummyCardstock()
            val channel = this@GameSpec.makeChannel(listOf("Bert", "Ernie"))
            val game = DummyGame(cardstock, channel)
            on("accessing cardstock") {
                val gameCardstock = game.cardstock
                it("should be the same as it was constructed with") {
                    assertTrue(cardstock === gameCardstock)
                }
            }
            on("accessing channel") {
                val gameChannel = game.channel
                it("should be the same as it was constructed with") {
                    assertTrue(channel === gameChannel)
                }
            }
            on("sending a message") {
                val message = "Hi, Bert!"
                game.sendMessage(message)
                it("should send the anti-pinged message to the channel") {
                    val antiPinged = channel.antiPing(message)
                    verify(channel).sendMessage(eq(antiPinged))
                }
            }
            val user = this@GameSpec.makeUser("Bert")
            on("getting a player that is not in the game") {
                val player = game.getPlayer(user, false)
                it("should return null") {
                    assertNull(player)
                }
            }
            on("getting a player that is not in the game but should be created") {
                val player = game.getPlayer(user, true)
                it("should not be null") {
                    assertNotNull(player)
                }
                it("should be a DummyPlayer") {
                    assertTrue(player is DummyPlayer)
                }
            }
            on("getting a player that is in the game") {
                val player = game.getPlayer(user, false)
                it("should not be null") {
                    assertNotNull(player)
                }
                it("should be a DummyPlayer") {
                    assertTrue(player is DummyPlayer)
                }
            }
            on("removing that player by his user") {
                game.removePlayer(user)
                val size = game.players.size()
                it("should have a player count of 0") {
                    assertEquals(0, size)
                }
                it("should return null on getting the player") {
                    assertNull(game.getPlayer(user, false))
                }
            }
            on("removing that player by his player") {
                val player = game.getPlayer(user, true)!!
                game.removePlayer(player)
                val size = game.players.size()
                it("should have a player count of 0") {
                    assertEquals(0, size)
                }
                it("should return null on getting the player") {
                    assertNull(game.getPlayer(user, false))
                }
            }
        }
    }
}
