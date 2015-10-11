/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.implementations

import xyz.cardstock.cardstock.Cardstock
import xyz.cardstock.cardstock.commands.CommandRegistrar
import xyz.cardstock.cardstock.configuration.CommandLineConfiguration
import xyz.cardstock.cardstock.configuration.Configuration
import xyz.cardstock.cardstock.games.GameRegistrar
import java.util.logging.Logger

internal class DummyCardstock : Cardstock() {
    override val configuration: Configuration
        get() = throw UnsupportedOperationException()
    override val commandLineConfiguration: CommandLineConfiguration
        get() = throw UnsupportedOperationException()
    override val commandRegistrar: CommandRegistrar
        get() = throw UnsupportedOperationException()
    override val gameRegistrar: GameRegistrar<*, *, *>
        get() = throw UnsupportedOperationException()
    override val logger: Logger = Logger.getLogger("xyz.cardstock.cardstock.implementations.DummyCardstock")

    internal fun doSetUpLogger() {
        this.setUpLogger()
    }
}
