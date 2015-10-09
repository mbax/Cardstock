/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.configuration

// TODO: KDoc
data class Server(
    val host: String,
    val port: Int,
    val secure: Boolean,
    val nickname: String,
    val prefix: Char,
    val user: String? = null,
    val realName: String? = null,
    val password: String? = null,
    val channels: List<String>? = null
)
