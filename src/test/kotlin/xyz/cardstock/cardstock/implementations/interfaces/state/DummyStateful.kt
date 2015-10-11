/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.implementations.interfaces.state

import com.google.common.collect.Lists
import xyz.cardstock.cardstock.interfaces.states.State
import xyz.cardstock.cardstock.interfaces.states.Stateful

internal class DummyStateful(override var state: State) : Stateful {
    override val stateListeners = Lists.newArrayList<(State) -> Unit>()

    internal enum class DummyState {
        FIRST,
        MIDDLE,
        LAST
    }
}
