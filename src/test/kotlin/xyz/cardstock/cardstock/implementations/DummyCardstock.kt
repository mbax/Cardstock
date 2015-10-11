/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.implementations

import org.kitteh.irc.client.library.Client
import xyz.cardstock.cardstock.Cardstock
import xyz.cardstock.cardstock.commands.CommandRegistrar
import xyz.cardstock.cardstock.configuration.CommandLineConfiguration
import xyz.cardstock.cardstock.configuration.Configuration
import xyz.cardstock.cardstock.configuration.Server
import xyz.cardstock.cardstock.games.GameRegistrar
import java.util.logging.Logger
import kotlin.reflect.declaredMemberProperties
import kotlin.reflect.jvm.isAccessible

internal open class DummyCardstock : Cardstock() {
    override val configuration: Configuration
        get() = throw UnsupportedOperationException()
    override val commandLineConfiguration: CommandLineConfiguration
        get() = throw UnsupportedOperationException()
    override val commandRegistrar: CommandRegistrar = CommandRegistrar()
    override val gameRegistrar: GameRegistrar<*, *, *>
        get() = throw UnsupportedOperationException()
    override val logger: Logger = Logger.getLogger("xyz.cardstock.cardstock.implementations.DummyCardstock")

    internal fun doSetUpLogger() {
        this.setUpLogger()
    }

    internal fun addClientServerMapping(client: Client, server: Server) {
        @Suppress("UNCHECKED_CAST")
        val map = Cardstock::class.declaredMemberProperties.first { it.name == "_clientServerMap" }.apply { this.isAccessible = true }.get(this) as MutableMap<Client, Server>
        map.put(client, server)
    }

    internal fun addClient(client: Client) {
        @Suppress("UNCHECKED_CAST")
        val list = Cardstock::class.declaredMemberProperties.first { it.name == "_clients" }.apply { this.isAccessible = true }.get(this) as MutableList<Client>
        list.add(client)
    }
}
