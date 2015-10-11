/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.extensions.list

import org.jetbrains.spek.api.shouldThrow
import xyz.cardstock.cardstock.MavenSpek
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ExtensionsSpec : MavenSpek() {
    override fun test() {
        given("the list [\"Hello\", \"friendly\", \"world\", \"people!\"]") {
            val list = listOf("Hello", "friendly", "world", "people!")
            on("get 0, 2") {
                // Note: Have to use #get at least once, instead of operator syntax. Otherwise coverage doesn't notice
                //       it being used
                val result = list.get(0, 2)
                it("should be [\"Hello\", \"friendly\"") {
                    assertEquals(listOf("Hello", "friendly"), result)
                }
            }
            on("get -1, null") {
                val result = list[-1, null]
                it("should be [\"people!\"]") {
                    assertEquals(listOf("people!"), result)
                }
            }
            on("get null, -1") {
                val result = list[null, -1]
                it("should be [\"Hello\", \"friendly\", \"world\"]") {
                    assertEquals(listOf("Hello", "friendly", "world"), result)
                }
            }
            on("get -3, -1") {
                val result = list[-3, -1]
                it("should be [\"friendly\", \"world\"]") {
                    assertEquals(listOf("friendly", "world"), result)
                }
            }
            on("get -1, -3") {
                it("should throw a IllegalArgumentException") {
                    shouldThrow(IllegalArgumentException::class.java) {
                        list[-1, -3]
                    }
                }
            }
            on("get -1, -3, true") {
                val result = list[-1, -3, true]
                it("should be an empty list") {
                    assertTrue(result.isEmpty())
                }
            }
        }
    }
}
