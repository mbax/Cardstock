/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock

import com.google.common.collect.Lists
import com.google.common.collect.Maps
import org.kitteh.irc.client.library.Client
import xyz.cardstock.cardstock.commands.CommandRegistrar
import xyz.cardstock.cardstock.configuration.CommandLineConfiguration
import xyz.cardstock.cardstock.configuration.Configuration
import xyz.cardstock.cardstock.configuration.Server
import xyz.cardstock.cardstock.games.GameRegistrar
import xyz.cardstock.cardstock.listeners.CommandListener
import java.util.Collections
import java.util.logging.ConsoleHandler
import java.util.logging.Formatter
import java.util.logging.LogRecord
import java.util.logging.Logger

/**
 * The base for Cardstock. Bots should extend this class.
 */
public abstract class Cardstock {

    /**
     * The commandline configuration for the bot. This should load configuration from the command line on construction.
     */
    abstract val commandLineConfiguration: CommandLineConfiguration
    /**
     * The file-based configuration for the bot.
     */
    abstract val configuration: Configuration
    /**
     * The command registrar that [CommandListener] uses for finding commands. Generally, an instance of
     * [CommandRegistrar] should work.
     */
    abstract val commandRegistrar: CommandRegistrar
    /**
     * The game registrar that should be used for registering games and keeping track of Channel to Game relationships.
     */
    abstract val gameRegistrar: GameRegistrar<*, *, *>
    /**
     * Mutable list for adding clients to.
     */
    private val _clients = Lists.newArrayList<Client>()
    /**
     * The IRC clients for this bot.
     */
    val clients: List<Client>
        get() = Collections.unmodifiableList(this._clients)
    /**
     * Mutable list for adding pairings to.
     */
    private val _clientServerMap = Maps.newHashMap<Client, Server>()
    /**
     * A map of clients to the server information they were created with.
     */
    val clientServerMap: Map<Client, Server>
        get() = Collections.unmodifiableMap(this._clientServerMap)
    /**
     * The [Logger] instance for this bot to use. Will be modified by [setUpLogger].
     */
    abstract val logger: Logger
    /**
     * The shutdown hook that Cardstock registers to quit from IRC when the JVM shuts down. Hooks can be added to this
     * in [CardstockShutdownHook.beginningHooks] and [CardstockShutdownHook.endHooks].
     */
    val shutdownHook = CardstockShutdownHook(this)

    // TODO: KDoc
    fun start() {
        this.setUpLogger()
        // Set up IRC clients
        val commandListener = CommandListener(this)
        for (server in this.commandLineConfiguration.configuration.servers) {
            val clientBuilder = Client.builder()
                .serverHost(server.host)
                .serverPort(server.port)
                .nick(server.nickname)
                .user(server.user ?: server.nickname)
                .realName(server.realName ?: server.nickname)
                .secure(server.secure)
                .messageDelay(1)
            server.password?.let { clientBuilder.serverPassword(it) }
            val client = clientBuilder.build()
            client.eventManager.registerEventListener(commandListener)
            server.channels?.let { client.addChannel(*it.toTypedArray()) }
            this._clients.add(client)
            this._clientServerMap.put(client, server)
        }
        // Add shutdown hook to wrap things up when the bot is killed
        Runtime.getRuntime().addShutdownHook(Thread(this.shutdownHook))
    }

    /**
     * Sets up [logger] to output messages like "&#91;INFO] Hello!"
     */
    private fun setUpLogger() {
        val ch = ConsoleHandler()
        ch.formatter = object : Formatter() {
            override fun format(logRecord: LogRecord) = "[${logRecord.level.localizedName}] ${logRecord.message}\n"
        }
        this.logger.useParentHandlers = false
        this.logger.addHandler(ch)
    }

}
