/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.extensions.string

import xyz.cardstock.cardstock.MavenSpek
import kotlin.test.assertEquals

class ExtensionsSpec : MavenSpek() {
    override fun test() {
        given("the string \"dog\"") {
            val dog = "dog"
            on("pluralization with amount 2") {
                val plural = dog.plural(2)
                it("should be \"dogs\"") {
                    assertEquals("dogs", plural)
                }
            }
            on("pluralization with amount 1") {
                val plural = dog.plural(1)
                it("should be \"dog\"") {
                    assertEquals("dog", plural)
                }
            }
            on("pluralization with amount 0") {
                val plural = dog.plural(0)
                it("should be \"dogs\"") {
                    assertEquals("dogs", plural)
                }
            }
            on("pluralization with amount 2 and prepending") {
                val plural = dog.plural(2, prepend = true)
                it("should be \"2 dogs\"") {
                    assertEquals("2 dogs", plural)
                }
            }
        }
        given("the string \"index\"") {
            val index = "index"
            on("pluralization with amount 2") {
                val plural = index.plural(2, "ices", 2)
                it("should be \"indices\"") {
                    assertEquals("indices", plural)
                }
            }
            on("pluralization with amount 1") {
                val plural = index.plural(1, "ices", 2)
                it("should be \"index\"") {
                    assertEquals("index", plural)
                }
            }
            on("pluralization with amount 0") {
                val plural = index.plural(0, "ices", 2)
                it("should be \"indices\"") {
                    assertEquals("indices", plural)
                }
            }
            on("pluralization with amount 2 and prepending") {
                val plural = index.plural(2, "ices", 2, true)
                it("should be \"indices\"") {
                    assertEquals("2 indices", plural)
                }
            }
        }
    }
}
