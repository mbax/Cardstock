/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.commands

/**
 * Defines metadata for commands. Must be used on the class of any command that is meant to be registered.
 */
@Target(AnnotationTarget.CLASS)
annotation class Command(
    /**
     * The name of this command. To use this command, the bot prefix, `!` for example, is prepended to the name and sent
     * to the bot. For example: `!myname`
     *
     * This must be unique.
     */
    val name: String,
    /**
     * Aliases for the name of this command. These may be used instead of the name to use the command.
     *
     * These must all be unique.
     */
    val aliases: Array<String>,
    /**
     * The description of this command. Displayed in help texts.
     */
    val description: String = "",
    /**
     * The usage for this command. Displayed in help texts.
     */
    val usage: String = "<command>",
    /**
     * The command type of this command. Defines where this command may be used.
     */
    val commandType: BaseCommand.CommandType = BaseCommand.CommandType.BOTH
)
