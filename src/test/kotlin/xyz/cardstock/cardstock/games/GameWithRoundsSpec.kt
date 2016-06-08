/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.games

import org.jetbrains.spek.api.Spek
import org.kitteh.irc.client.library.element.Channel
import org.mockito.Matchers.anyString
import org.powermock.api.mockito.PowerMockito.*
import xyz.cardstock.cardstock.implementations.DummyCardstock
import xyz.cardstock.cardstock.implementations.games.DummyGameWithRounds
import xyz.cardstock.cardstock.implementations.games.rounds.DummyRound
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class GameWithRoundsSpec : Spek({

    fun makeChannel(nicknames: List<String>): Channel {
        val channel = mock(Channel::class.java)
        doNothing().`when`(channel).sendMessage(anyString())
        `when`(channel.nicknames).thenReturn(nicknames)
        return channel
    }

    given("a DummyGameWithRounds") {
        val cardstock = DummyCardstock()
        val channel = makeChannel(listOf("Bert", "Ernie"))
        val game = DummyGameWithRounds(cardstock, channel)
        on("construction") {
            val round = game.currentRound
            it("should have no current round") {
                assertNull(round)
            }
        }
        on("setting the round") {
            val dummyRound = DummyRound(game, 1)
            game.currentRound = dummyRound
            val round = game.currentRound
            it("should not be null") {
                assertNotNull(round)
            }
            it("should have the same round as it was set to") {
                assertTrue(dummyRound === round)
            }
        }
    }
})
