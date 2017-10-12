/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.listeners

import org.kitteh.irc.client.library.element.User
import org.kitteh.irc.client.library.event.channel.ChannelMessageEvent
import org.kitteh.irc.client.library.event.helper.ActorEvent
import org.kitteh.irc.client.library.event.helper.MessageEvent
import org.kitteh.irc.client.library.event.user.PrivateMessageEvent
import net.engio.mbassy.listener.Handler
import xyz.cardstock.cardstock.Cardstock
import xyz.cardstock.cardstock.commands.BaseCommand
import xyz.cardstock.cardstock.commands.CallInfo
import xyz.cardstock.cardstock.extensions.list.get
import xyz.cardstock.cardstock.extensions.string.get

class CommandListener(val cardstock: Cardstock) {

    /**
     * A data class for a command and pertinent information about it sent in a message.
     * @constructor Makes a new sent command.
     * @param[command] The command sent in the message
     * @param[label] The name used for the command. There is no prefix in this.
     * @param[arguments] A space-delimited list of arguments provided with this command. May be empty.
     */
    private data class SentCommand(val command: BaseCommand, val label: String, val arguments: List<String>)

    /**
     * Gets the command that was sent in this event. If no command was sent, returns `null`.
     */
    private fun getSentCommand(event: MessageEvent): SentCommand? {
        if (event.message.isEmpty()) return null
        val parts = event.message.split(" ")
        val labelWithPrefix = parts[0]
        if (labelWithPrefix[0] != this.cardstock.clientServerMap[event.client]!!.prefix) return null
        val label = labelWithPrefix[1, null]
        val command = this.cardstock.commandRegistrar[label] ?: return null
        return SentCommand(command, label, parts[1, null])
    }

    /**
     * Runs the command in [sentCommand] if it should be run.
     */
    private fun executeCommand(sentCommand: SentCommand, event: ActorEvent<User>, usageType: CallInfo.UsageType) {
        val commandType = sentCommand.command.commandType
        if (commandType != BaseCommand.CommandType.BOTH && commandType.name != usageType.name) return
        try {
            sentCommand.command.run(event, CallInfo(sentCommand.label, usageType), sentCommand.arguments)
        } catch (t: Throwable) {
            t.printStackTrace()
            val message = "Unhandled command exception! ${t.javaClass.simpleName}: ${t.message}"
            event.actor.sendNotice(message)
            this.cardstock.logger.warning(message)
        }
    }

    /**
     * Handles commands sent in channels.
     */
    @Handler
    fun onChannelCommand(event: ChannelMessageEvent) {
        val sentCommand = this.getSentCommand(event) ?: return
        this.executeCommand(sentCommand, event, CallInfo.UsageType.CHANNEL)
    }

    /**
     * Handles commands sent to the bot.
     */
    @Handler
    fun onPrivateCommand(event: PrivateMessageEvent) {
        val sentCommand = this.getSentCommand(event) ?: return
        this.executeCommand(sentCommand, event, CallInfo.UsageType.PRIVATE)
    }

}
