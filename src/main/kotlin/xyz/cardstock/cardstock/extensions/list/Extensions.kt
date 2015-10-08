/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.extensions.list

/**
 * Python-esque slicing.
 */
operator public fun <T> List<T>.get(start: Int?, end: Int?): List<T> {
    var realEnd = end ?: this.size()
    var realStart = start ?: 0
    if (realEnd < 0) {
        realEnd += this.size()
    }
    if (realStart < 0) {
        realStart += this.size()
    }
    return this.subList(realStart, realEnd)
}
