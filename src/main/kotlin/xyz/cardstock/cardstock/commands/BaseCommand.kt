/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.commands

import org.kitteh.irc.client.library.element.User
import org.kitteh.irc.client.library.event.helper.ActorEvent
import java.util.Objects

/**
 * The base for all commands. All commands must be annotated on the class level with [Command] to provide metadata about
 * that command. If this is not the case, the command will never work and will throw [NullPointerException]s often.
 */
abstract class BaseCommand {

    /**
     * Performs the action that this command is supposed to perform. Called every time that this command is used.
     */
    abstract fun run(event: ActorEvent<User>, callInfo: CallInfo, arguments: List<String>)

    /**
     * Gets the Command annotation on this command.
     * @return The annotation for this command, or null if none was specified.
     * @see Command
     */
    private fun getCommandAnnotation(): Command? = this.javaClass.getDeclaredAnnotation(Command::class.java)

    /**
     * The name of this command. Defined by annotation.
     * @see Command
     */
    open val name: String
        get() = this.getCommandAnnotation()!!.name

    /**
     * The aliases for this command. Defined by annotation.
     * @see Command
     */
    open val aliases: Array<String>
        get() = this.getCommandAnnotation()!!.aliases

    /**
     * The description of this command. Defined by annotation.
     */
    open val description: String
        get() = this.getCommandAnnotation()!!.description

    /**
     * The usage of this command. Defined by annotation.
     *
     * Usage should resemble something like `"&lt;command&gt; [requiredParameter] (optionalParameter)"`
     */
    open val usage: String
        get() = this.getCommandAnnotation()!!.usage

    /**
     * The command type of this command. Defined by annotation.
     */
    open val commandType: CommandType
        get() = this.getCommandAnnotation()!!.commandType

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is BaseCommand) return false
        return this.name == other.name && this.aliases.toList() == other.aliases.toList() && this.description == other.description && this.usage == other.usage
    }

    override fun hashCode() = Objects.hash(this.name, this.aliases, this.description, this.usage)

    /**
     * An enum defining where commands may be used.
     */
    enum class CommandType {
        /**
         * A command with this type may be used only in channels.
         */
        CHANNEL,
        /**
         * A command with this type may be used only in private messages between the bot and a [User].
         */
        PRIVATE,
        /**
         * A command with this type may be used in both channels and private messages.
         */
        BOTH
    }

}
