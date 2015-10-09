/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.configuration

/**
 * A data class representing a server to connect to.
 */
data class Server(
    /**
     * The hostname of this server.
     */
    val host: String,
    /**
     * The port of this server.
     */
    val port: Int,
    /**
     * If this server should be connected to over SSL.
     */
    val secure: Boolean,
    /**
     * The nickname that the bot should use on this server.
     */
    val nickname: String,
    /**
     * The prefix for commands on this server.
     */
    val prefix: Char,
    /**
     * The user of the bot on this server. If null, defaults to [nickname].
     */
    val user: String? = null,
    /**
     * The real name of the bot on this server. If null, defaults to [nickname].
     */
    val realName: String? = null,
    /**
     * The password to use to connect to this server. If null, use no password.
     */
    val password: String? = null,
    /**
     * The channels to join on connection to this server. If null or empty, join no channels on connection.
     */
    val channels: List<String>? = null
)
