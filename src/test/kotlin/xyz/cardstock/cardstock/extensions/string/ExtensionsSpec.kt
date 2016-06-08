/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.extensions.string

import org.jetbrains.spek.api.Spek
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue
import xyz.cardstock.cardstock.extensions.string.get as gett

class ExtensionsSpec : Spek({
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
    given("the string \"Hello world!\"") {
        val helloWorld = "Hello world!"
        on("get 0, 2") {
            val result = helloWorld.gett(0, 2)
            it("should be \"He\"") {
                assertEquals("He", result)
            }
        }
        on("get -1, null") {
            val result = helloWorld.gett(-1, null)
            it("should be \"!\"") {
                assertEquals("!", result)
            }
        }
        on("get null, -1") {
            val result = helloWorld.gett(null, -1)
            it("should be \"Hello world\"") {
                assertEquals("Hello world", result)
            }
        }
        on("get -6, -1") {
            val result = helloWorld.gett(-6, -1)
            it("should be \"world\"") {
                assertEquals("world", result)
            }
        }
        on("get -1, -6") {
            it("should throw a StringIndexOutOfBoundsException") {
                assertFailsWith(StringIndexOutOfBoundsException::class) {
                    helloWorld.gett(-1, -6)
                }
            }
        }
        on("get -1, -6, true") {
            val result = helloWorld.gett(-1, -6, true)
            it("should be an empty string") {
                assertTrue(result.isEmpty())
            }
        }
    }
})
