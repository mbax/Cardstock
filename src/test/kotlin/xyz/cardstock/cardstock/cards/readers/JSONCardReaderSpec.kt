/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.cards.readers

import org.json.JSONException
import xyz.cardstock.cardstock.MavenSpek
import xyz.cardstock.cardstock.cards.PointedCard
import java.io.File
import kotlin.test.assertEquals

class JSONCardReaderSpec : MavenSpek() {
    override fun test() {
        given("a JSONCardReader that maps to TestCard and data that has valid and invalid cards") {
            val cardReader = JSONCardReader<PointedCard>(File("src/test/resources/cards.json")) {
                return@JSONCardReader try {
                    TestCard(it.getInt("points"))
                } catch (ex: JSONException) {
                    null
                }
            }
            on("parse") {
                val cards = cardReader.cards
                it("should contain two cards") {
                    assertEquals(2, cards.size())
                }
                it("should contain one card with a point value of 1") {
                    assertEquals(1, cards.filter { it.points == 1 }.size())
                }
                it("should contain one card with a point value of 2") {
                    assertEquals(1, cards.filter { it.points == 2 }.size())
                }
            }
        }
    }

    private class TestCard(override val points: Int) : PointedCard
}
