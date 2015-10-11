/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.games

import org.kitteh.irc.client.library.element.Channel
import org.powermock.api.mockito.PowerMockito.mock
import xyz.cardstock.cardstock.MavenSpek
import xyz.cardstock.cardstock.implementations.DummyCardstock
import xyz.cardstock.cardstock.implementations.games.DummyGame
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class GameRegistrarSpec : MavenSpek() {

    private fun makeChannel(): Channel {
        val channel = mock(Channel::class.java)
        return channel
    }

    override fun test() {
        given("a standard game registrar") {
            val cardstock = DummyCardstock()
            val channel = this@GameRegistrarSpec.makeChannel()
            val gameRegistrar = GameRegistrar(cardstock, { cardstock, channel -> DummyGame(cardstock, channel) })
            on("registering a game") {
                val game = gameRegistrar.on(channel)
                it("should have one game") {
                    assertEquals(1, gameRegistrar.all().size())
                }
                it("should return the same game if queried by channel") {
                    assertTrue(game === gameRegistrar.find(channel))
                }
            }
            on("registering the same game again") {
                val oldGame = gameRegistrar.find(channel)
                val game = gameRegistrar.on(channel)
                it("should return the same game") {
                    assertTrue(oldGame === game)
                }
            }
        }
        given("a game registrar with one game") {
            val cardstock = DummyCardstock()
            val channel = this@GameRegistrarSpec.makeChannel()
            val gameRegistrar = GameRegistrar(cardstock, { cardstock, channel -> DummyGame(cardstock, channel) })
            gameRegistrar.on(channel)
            on("unregistering the same game") {
                gameRegistrar.end(channel)
                it("should have zero games") {
                    assertEquals(0, gameRegistrar.all().size())
                }
                it("should return null if queried by channel") {
                    assertNull(gameRegistrar.find(channel))
                }
            }
        }
        given("another game registrar with one game") {
            val cardstock = DummyCardstock()
            val channel = this@GameRegistrarSpec.makeChannel()
            val gameRegistrar = GameRegistrar(cardstock, { cardstock, channel -> DummyGame(cardstock, channel) })
            val game = gameRegistrar.on(channel)
            on("unregistering the same game") {
                gameRegistrar.end(game)
                it("should have zero games") {
                    assertEquals(0, gameRegistrar.all().size())
                }
                it("should return null if queried by channel") {
                    assertNull(gameRegistrar.find(channel))
                }
            }
        }
    }
}
