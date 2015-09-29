/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock

import org.kohsuke.args4j.CmdLineException
import org.kohsuke.args4j.CmdLineParser
import org.kohsuke.args4j.Option
import org.kohsuke.args4j.spi.CharOptionHandler
import org.kohsuke.args4j.spi.IntOptionHandler
import org.kohsuke.args4j.spi.StringArrayOptionHandler
import org.kohsuke.args4j.spi.StringOptionHandler

public open class Configuration(args: Array<String>, cardstock: Cardstock) {

    @field:Option(name = "-s", usage = "Server to connect to.", handler = StringOptionHandler::class, required = true)
    public lateinit var server: String
        private set

    @field:Option(name = "-p", usage = "Port of the server to connect to.", handler = IntOptionHandler::class)
    public var port: Int = 6667
        private set

    @field:Option(name = "-n", usage = "Nickname of the bot on the server.", handler = StringOptionHandler::class)
    public var nick: String = "Fictitious"
        private set

    @field:Option(name = "-c", usage = "List of channels to join on connect.", handler = StringArrayOptionHandler::class, required = true)
    public lateinit var channels: Array<String>
        private set

    @field:Option(name = "-z", usage = "Prefix to use for bot commands.", handler = CharOptionHandler::class)
    public var prefix: Char = '!'
        private set

    @field:Option(name = "-C", usage = "Paths pointing to JSON card files.", handler = StringArrayOptionHandler::class, required = true)
    public lateinit var cardFiles: Array<String>
        private set

    init {
        val parser = CmdLineParser(this)
        try {
            parser.parseArgument(*args)
        } catch (ex: CmdLineException) {
            cardstock.logger.info(ex.getMessage())
            ex.parser.printUsage(System.out)
            System.exit(1)
        }
    }
}
