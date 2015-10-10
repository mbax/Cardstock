/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.extensions.enum

import xyz.cardstock.cardstock.interfaces.states.State

/**
 * Converts this enum value to a [State]. [State.next] will be the next defined value in the enum or null if this value
 * is the end of the enum.
 */
fun Enum<*>.toState(): State = object : State {
    override val next: State?
        get() = with(this@toState.javaClass.getDeclaredMethod("values")(null) as Array<Enum<*>>) {
            val newPos = this@toState.ordinal() + 1
            if (newPos >= this.size()) null else this[newPos].toState()
        }
    override val uniqueName: String = this@toState.name()
}
