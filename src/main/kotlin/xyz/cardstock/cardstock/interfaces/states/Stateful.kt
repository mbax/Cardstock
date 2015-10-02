/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.interfaces.states

/**
 * Represents an object that holds a [State] at any given time.
 */
interface Stateful {

    /**
     * The current state of this object.
     */
    var state: State
    /**
     * An unmodifiable list of listeners. Each listener is invoked, in order, with the new state whenever [advanceState]
     * changes [state].
     */
    val stateListeners: MutableList<(State) -> Unit>

    /**
     * Advances this state of this object to the next state, based on [state]'s [State.next] property. If the next state
     * is null, this method does nothing. Otherwise, [runStateListeners] is called after updating [state].
     */
    open fun advanceState() {
        this.state = this.state.next ?: return
        this.runStateListeners()
    }

    /**
     * Invokes all listeners in [stateListeners] with the current state as their input.
     */
    fun runStateListeners() {
        this.stateListeners.forEach { it.invoke(this.state) }
    }

}
