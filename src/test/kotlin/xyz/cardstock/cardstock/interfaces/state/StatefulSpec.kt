/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.interfaces.state

import com.google.common.collect.Lists
import org.jetbrains.spek.api.Spek
import xyz.cardstock.cardstock.extensions.enums.toState
import xyz.cardstock.cardstock.implementations.interfaces.state.DummyStateful
import kotlin.test.assertEquals

class StatefulSpec : Spek({
    given("a Stateful class on the first dummy state") {
        val dummyStateful = DummyStateful(DummyStateful.DummyState.FIRST.toState())
        val results = Lists.newArrayList<String>()
        on("adding two state listeners") {
            dummyStateful.stateListeners.add {
                results.add("First")
            }
            dummyStateful.stateListeners.add {
                results.add("Second")
            }
            it("should have two state listeners") {
                assertEquals(2, dummyStateful.stateListeners.size)
            }
        }
        on("advancing its state") {
            dummyStateful.advanceState()
            it("should call all state listeners in order") {
                assertEquals(2, results.size)
                assertEquals("First", results[0])
                assertEquals("Second", results[1])
            }
            it("should be on the second dummy state") {
                assertEquals(DummyStateful.DummyState.MIDDLE.name, dummyStateful.state.uniqueName)
            }
        }
    }
    given("a Stateful class on the last dummy state") {
        val dummyStateful = DummyStateful(DummyStateful.DummyState.LAST.toState())
        on("advancing its state") {
            dummyStateful.advanceState()
            it("should stay on the same state") {
                assertEquals(DummyStateful.DummyState.LAST.name, dummyStateful.state.uniqueName)
            }
        }
    }
})
