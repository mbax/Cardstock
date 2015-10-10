/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.interfaces.states

/**
 * A state that a [Stateful] class may have.
 */
interface State {

    /**
     * The next [State] after this state. This should only return `null` when this is the last state.
     */
    val next: State?
    /**
     * The unique name for this [State]. Generally used for [Stateful] class listeners in order to differentiate between
     * different states.
     */
    val uniqueName: String

}
