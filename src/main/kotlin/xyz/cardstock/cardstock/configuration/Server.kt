/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.configuration

data class Server(
    val host: String,
    val port: Int,
    val nickname: String,
    val user: String? = null,
    val password: String? = null,
    val channels: List<String>? = null
)
