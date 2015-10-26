/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.commands

import com.google.common.collect.Maps
import java.util.Collections

/**
 * The command registrar is where commands are registered for use. Generally queried by
 * [CommandListener][xyz.cardstock.cardstock.listeners.CommandListener].
 */
class CommandRegistrar {

    /**
     * The commands registered. Keys are names.
     */
    private val commands = Maps.newHashMap<String, BaseCommand>()

    /**
     * Just a quick way to use bracket notation instead of the [getCommand] method.
     */
    operator fun get(name: String) = this.getCommand(name.toLowerCase())

    /**
     * Gets all registered commands in an unmodifiable set.
     */
    fun all(): Set<BaseCommand> = Collections.unmodifiableSet(this.commands.values.toSet())

    /**
     * Gets a command by its [name]. [name] may be a name or an alias. Will return `null` if no such command has that
     * name or alias.
     */
    fun getCommand(name: String): BaseCommand? = this.commands[name] ?: this.getCommandByAlias(name)

    /**
     * Gets a command by its [alias]. Returns `null` if no such alias is registered.
     */
    fun getCommandByAlias(alias: String): BaseCommand? = this.commands.values.filter { it.aliases.any { a -> a.equals(alias, ignoreCase = true) } }.firstOrNull()

    /**
     * Registers [command] in this registrar.
     * @throws IllegalStateException If a command with the same name or a match alias is already registered.
     */
    fun registerCommand(command: BaseCommand) {
        check(this.getCommand(command.name) == null) { "Command with the name \"${command.name}\" already registered." }
        for (alias in command.aliases) {
            check(this.getCommandByAlias(alias) == null) { "Command with the alias \"$alias\" already registered." }
        }
        this.commands.put(command.name.toLowerCase(), command)
    }

    /**
     * Unregisters the command with the given [name]. Returns the removed command or `null`.
     */
    fun unregisterCommand(name: String): BaseCommand? = this.commands.remove(name.toLowerCase())

    /**
     * Unregisters [command]. Returns the removed command or `null`.
     */
    fun unregisterCommand(command: BaseCommand): BaseCommand? = this.unregisterCommand(command.name)


}
