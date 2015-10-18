/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.extensions.jsonobject

import org.json.JSONObject

/**
 * Gets the key from this JSONObject or a default from a different JSONObject. If both return null, this will throw
 * an [IllegalStateException].
 */
fun <T : Any> JSONObject.getWithDefaults(key: String, defaults: JSONObject, mapper: (String, JSONObject) -> T?): T {
    val result = mapper(key, this) ?: mapper(key, defaults)
    return result ?: throw IllegalStateException("$key must be defined")
}

/**
 * Gets the key from this JSONObject or a default from a different JSONObject. If both return null, this will return
 * `null`.
 */
fun <T> JSONObject.optWithDefaults(key: String, defaults: JSONObject, mapper: (String, JSONObject) -> T): T {
    return mapper(key, this) ?: mapper(key, defaults)
}
