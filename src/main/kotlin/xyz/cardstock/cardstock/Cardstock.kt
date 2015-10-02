/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock

import org.kitteh.irc.client.library.Client
import xyz.cardstock.cardstock.commands.CommandRegistrar
import xyz.cardstock.cardstock.listeners.CommandListener
import java.util.logging.ConsoleHandler
import java.util.logging.Formatter
import java.util.logging.LogRecord
import java.util.logging.Logger

/**
 * The base for Cardstock. Bots should extend this class.
 */
public abstract class Cardstock {

    /**
     * The configuration for the bot. This should load configuration from the command line on construction.
     */
    abstract val configuration: Configuration
    /**
     * The command registrar that [CommandListener] uses for finding commands. Generally, an instance of
     * [CommandRegistrar] should work.
     */
    abstract val commandRegistrar: CommandRegistrar
    /**
     * The IRC client for this bot.
     */
    lateinit var client: Client
        private set
    /**
     * The [Logger] instance for this bot to use. Will be modified by [setUpLogger].
     */
    abstract val logger: Logger

    fun start() {
        this.setUpLogger()
        // Set up IRC client
        this.client = Client.builder()
            .server(this.configuration.server)
            .server(this.configuration.port)
            .nick(this.configuration.nick)
            .messageDelay(1)
            .build()
        // Add command listener
        this.client.eventManager.registerEventListener(CommandListener(this))
        // Add channels
        this.client.addChannel(*this.configuration.channels)
        // Add shutdown hook to wrap things up when the bot is killed
        Runtime.getRuntime().addShutdownHook(Thread(CardstockShutdownHook(this)))
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
