/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.cards.readers

import org.jetbrains.spek.api.Spek
import org.json.JSONException
import org.json.JSONObject
import xyz.cardstock.cardstock.cards.PointedCard
import xyz.cardstock.cardstock.implementations.cards.TestCard
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class JSONCardReaderSpec : Spek({
    val mapper = { obj: JSONObject ->
        try {
            TestCard(obj.getInt("points"))
        } catch (ex: JSONException) {
            null
        }
    }

    given("a JSONCardReader that maps to TestCard and data that has valid and invalid cards") {
        val cardReader = JSONCardReader<PointedCard>(File("src/test/resources/cards.json"), mapper)
        on("construction") {
            it("should have the same mapper as constructed with") {
                assertTrue(mapper === cardReader.mapper)
            }
        }
        on("mapping a valid test card") {
            val testCard = JSONObject("{\"points\": 2}")
            val result = mapper(testCard)
            it("should not be null") {
                assertNotNull(result)
            }
            it("should produce a TestCard") {
                assertTrue(result is TestCard)
            }
            it("should have a point value of 2") {
                assertEquals(2, result!!.points)
            }
        }
        on("mapping an invalid test card") {
            val testCard = JSONObject("{\"invalid\": true}")
            val result = mapper(testCard)
            it("should be null") {
                assertNull(result)
            }
        }
        on("parse") {
            val cards = cardReader.cards
            it("should contain two cards") {
                assertEquals(2, cards.size)
            }
            it("should contain one card with a point value of 1") {
                assertEquals(1, cards.filter { it.points == 1 }.size)
            }
            it("should contain one card with a point value of 2") {
                assertEquals(1, cards.filter { it.points == 2 }.size)
            }
        }
    }
    given("the same reader with an invalid dataset") {
        val cardReader = JSONCardReader<PointedCard>(File("src/test/resources/bad_cards.json"), mapper)
        on("parse") {
            val cards = cardReader.cards
            it("should contain one card") {
                assertEquals(1, cards.size)
            }
            it("should contain one card with a point value of 1") {
                assertEquals(1, cards.filter { it.points == 1 }.size)
            }
        }
    }
})
