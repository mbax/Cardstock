/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.implementations

import xyz.cardstock.cardstock.configuration.CommandLineConfiguration

class StartableDummyCardstock(args: Array<String>) : DummyCardstock() {
    override val commandLineConfiguration = CommandLineConfiguration(args, this)
    override val configuration = this.commandLineConfiguration.configuration
}
