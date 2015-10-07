/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.commands

import com.google.common.collect.Maps
import java.util.Collections

class CommandRegistrar {

    private val commands = Maps.newHashMap<String, BaseCommand>()

    /**
     * Just a quick way to use bracket notation instead of the #getCommand(String) method.
     */
    operator fun get(name: String) = this.getCommand(name)

    fun all(): Set<BaseCommand> = Collections.unmodifiableSet(this.commands.values().toSet())

    fun getCommand(name: String): BaseCommand? = this.commands[name] ?: this.getCommandByAlias(name)

    fun getCommandByAlias(alias: String): BaseCommand? =
        this.commands.values().filter { alias in it.aliases }.firstOrNull()

    fun registerCommand(command: BaseCommand) {
        check(this.getCommand(command.name) == null) { -> "Command with the name \"${command.name}\" already registered." }
        for (alias in command.aliases) {
            check(this.getCommandByAlias(alias) == null) { -> "Command with the alias \"$alias\" already registered." }
        }
        this.commands.put(command.name, command)
    }

    fun unregisterCommand(name: String): BaseCommand? = this.commands.remove(name)

    fun unregisterCommand(command: BaseCommand): BaseCommand? = this.unregisterCommand(command.name)


}
