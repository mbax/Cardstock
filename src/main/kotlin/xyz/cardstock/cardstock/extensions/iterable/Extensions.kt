/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.extensions.iterable

/**
 * Throws an [IllegalStateException] with [message] if any object in this Iterable results in [predicate] returning
 * `false`.
 */
fun <T> Iterable<T>.ensure(message: String, predicate: (T) -> Boolean): Iterable<T> {
    this.forEach {
        check(predicate(it), { -> message })
    }
    return this
}
