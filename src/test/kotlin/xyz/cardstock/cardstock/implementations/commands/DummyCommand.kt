/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.implementations.commands

import xyz.cardstock.cardstock.commands.BaseCommand

abstract class DummyCommand(
    override val name: String,
    override val aliases: Array<String> = arrayOf(),
    override val description: String = "",
    override val usage: String = "<command>",
    override val commandType: BaseCommand.CommandType = BaseCommand.CommandType.BOTH
) : BaseCommand()
