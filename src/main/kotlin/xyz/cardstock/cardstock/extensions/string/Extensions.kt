/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.extensions.string

/**
 * A Python-esque syntax for substrings.
 *
 * - `Python` -> `Java`
 * - `a[1:5]` -> `a[1, 5]`
 * - `a[:5]` -> `a[null, 5]`
 * - `a[3:]` -> `a[3, null]`
 * - `a[:]` -> `a[null, null]`
 * - `a[:-1]` -> `a[null, -1]`
 * @param[start] The start index
 * @param[end] The end index
 * @param[emptyString] Whether to return an empty string (like Python) if the indices are invalid
 * @return The substring
 */
operator fun String.get(start: Int?, end: Int?, emptyString: Boolean = false): String {
    var realEnd = end ?: this.length()
    var realStart = start ?: 0
    if (realEnd < 0) {
        realEnd += this.length()
    }
    if (realStart < 0) {
        realStart += this.length()
    }
    return try {
        this.substring(realStart, realEnd)
    } catch (e: StringIndexOutOfBoundsException) {
        if (emptyString) "" else throw e
    }
}

/**
 * Makes this word plural by appending [suffix] after removing [remove] characters from the end if [amount] is not equal
 * to `1`. If [prepend] is true, [amount] and one space will be prepended on the return string.
 */
fun String.plural(amount: Int, suffix: String = "s", remove: Int = 0, prepend: Boolean = false): String {
    var newString = this
    if (prepend) {
        newString = "$amount " + newString
    }
    if (amount == 1) {
        return newString
    }
    if (remove > 0) {
        newString = newString[null, -remove]
    }
    return newString + suffix
}
