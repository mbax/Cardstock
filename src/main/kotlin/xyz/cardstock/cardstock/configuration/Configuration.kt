/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.configuration

import com.google.common.collect.Lists
import org.json.JSONObject
import xyz.cardstock.cardstock.extensions.jsonobject.getWithDefaults
import xyz.cardstock.cardstock.extensions.jsonobject.optWithDefaults
import java.nio.file.Files
import java.nio.file.Path
import java.util.Collections

/**
 * The file-based configuration for this bot.
 */
open class Configuration(path: Path) {

    /**
     * The actual JSON object that was read from the file.
     */
    protected val json: JSONObject = JSONObject(Files.readAllLines(path, Charsets.UTF_8).join("\n"))
    /**
     * The mutable list of servers specified in the `servers` JSON object.
     */
    protected val _servers = Lists.newArrayList<Server>()
    /**
     * Servers read from the `servers` JSON object in the configuration.
     */
    val servers: List<Server>
        get() = Collections.unmodifiableList(this._servers)

    init {
        val defaults = this.json.optJSONObject("defaults") ?: JSONObject()
        val listedServers = this.json.optJSONArray("servers")
        check(listedServers != null && listedServers.length() > 0, { -> "At least one server must be defined" })
        this._servers.addAll(
            listedServers
                .map {
                    if (it !is JSONObject) {
                        throw IllegalStateException("Invalid server defined. Servers must be JSON objects.")
                    }
                    // Cast is not useless. At least, IDEA gets all confused without it
                    @Suppress("USELESS_CAST")
                    return@map it as JSONObject
                }
                .map {
                    val host = it.getWithDefaults("host", defaults) { key, json -> json.optString(key, null) }
                    val port = it.getWithDefaults("port", defaults) { key, json -> json.opt(key) as Int? }
                    val secure = it.getWithDefaults("secure", defaults) { key, json -> json.opt(key) as Boolean? }
                    val nickname = it.getWithDefaults("nick", defaults) { key, json -> json.optString(key, null) }
                    val prefix = it.getWithDefaults("prefix", defaults) { key, json -> json.optString(key, null)?.charAt(0) }
                    val user = it.optWithDefaults("user", defaults) { key, json -> json.optString(key, null) }
                    val realName = it.optWithDefaults("realName", defaults) { key, json -> json.optString(key, null) }
                    val password = it.optWithDefaults("password", defaults) { key, json -> json.optString(key, null) }
                    val channels = it.optWithDefaults("channels", defaults) { key, json -> json.optJSONArray("channels")?.map { it.toString() } }
                    return@map Server(host, port, secure, nickname, prefix, user, realName, password, channels)
                }
        )
    }

}
