/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.configuration

import org.kohsuke.args4j.CmdLineException
import org.kohsuke.args4j.CmdLineParser
import org.kohsuke.args4j.Option
import org.kohsuke.args4j.spi.CharOptionHandler
import org.kohsuke.args4j.spi.FileOptionHandler
import org.kohsuke.args4j.spi.StringArrayOptionHandler
import org.kohsuke.args4j.spi.StringOptionHandler
import xyz.cardstock.cardstock.Cardstock
import java.io.File

// TODO: KDoc
open class Configuration(args: Array<String>, cardstock: Cardstock) {

    @field:Option(name = "-n", usage = "Nickname of the bot on the server.", handler = StringOptionHandler::class)
    var nick: String = "CardstockBot"
        private set

    @field:Option(name = "-z", usage = "Prefix to use for bot commands.", handler = CharOptionHandler::class)
    var prefix: Char = '!'
        private set

    @field:Option(name = "-C", usage = "Paths pointing to JSON card files.", handler = StringArrayOptionHandler::class, required = true)
    lateinit var cardFiles: Array<String>
        private set

    @field:Option(name = "-c", usage = "File containing bot configuration.", handler = FileOptionHandler::class, required = true)
    lateinit var configurationFile: File
        private set

    val serverConfigurations: ServerConfigurations
        get() = ServerConfigurations(this.configurationFile)

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
