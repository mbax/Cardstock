/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.implementations.commands

import org.kitteh.irc.client.library.element.User
import org.kitteh.irc.client.library.event.helper.ActorEvent
import xyz.cardstock.cardstock.commands.BaseCommand
import xyz.cardstock.cardstock.commands.CallInfo
import xyz.cardstock.cardstock.commands.Command

@Command(
    name = "completetest",
    aliases = arrayOf(
        "testcomplete"
    ),
    description = "A test command.",
    usage = "<command> [something]",
    commandType = BaseCommand.CommandType.CHANNEL
)
internal class CompleteTestCommand : BaseCommand() {
    override fun run(event: ActorEvent<User>, callInfo: CallInfo, arguments: List<String>) {
        throw UnsupportedOperationException()
    }
}
