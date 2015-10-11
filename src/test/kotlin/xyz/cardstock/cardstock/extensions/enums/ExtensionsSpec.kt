/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.extensions.enums

import xyz.cardstock.cardstock.MavenSpek
import xyz.cardstock.cardstock.interfaces.states.State
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ExtensionsSpec : MavenSpek() {
    override fun test() {
        given("the first element of an enum") {
            val first = TestState.ONE
            on("toState()") {
                val state = first.toState()
                it("should be a State") {
                    assertTrue(state is State)
                }
                it("should have the unique name of the first enum value") {
                    assertEquals(TestState.ONE.name(), state.uniqueName)
                }
                it("should have a next value") {
                    assertNotNull(state.next)
                }
                it("should have a next value of the next element in the enum") {
                    assertEquals(TestState.TWO.name(), state.next!!.uniqueName)
                }
            }
        }
        given("the last element of an enum") {
            val last = TestState.THREE
            on("toState()") {
                val state = last.toState()
                it("should be a State") {
                    assertTrue(state is State)
                }
                it("should have a unique name of the last enum value") {
                    assertEquals(TestState.THREE.name(), state.uniqueName)
                }
                it("should not have a next value") {
                    assertNull(state.next)
                }
            }
        }
    }

    internal enum class TestState {
        ONE,
        TWO,
        THREE
    }
}
