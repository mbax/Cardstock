/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.implementations.commands

import org.kitteh.irc.client.library.element.User
import org.kitteh.irc.client.library.event.helper.ActorEvent
import xyz.cardstock.cardstock.commands.CallInfo

internal open class TestCommand(var throwException: Boolean = true) : DummyCommand("test", arrayOf("spec")) {

    internal var wasRun = false

    override fun run(event: ActorEvent<User>, callInfo: CallInfo, arguments: List<String>) {
        this.wasRun = true
        if (this.throwException) {
            throw TestCommandException()
        }
    }
}
