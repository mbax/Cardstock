/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.configuration

import org.kohsuke.args4j.CmdLineException
import org.kohsuke.args4j.CmdLineParser
import org.kohsuke.args4j.Option
import org.kohsuke.args4j.spi.FileOptionHandler
import xyz.cardstock.cardstock.Cardstock
import java.io.File

/**
 * The configuration for this bot, as specified on the command line.
 */
open class CommandLineConfiguration(args: Array<String>, cardstock: Cardstock) {

    /**
     * The file pointing to the JSON configuration.
     */
    @field:Option(name = "-c", usage = "File containing bot configuration.", handler = FileOptionHandler::class, required = true)
    lateinit var configurationFile: File
        private set

    /**
     * Gets the [Configuration], as specified by [configurationFile].
     */
    val configuration: Configuration
        get() = Configuration(this.configurationFile)

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
