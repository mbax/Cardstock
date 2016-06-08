/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.extensions.user

import org.jetbrains.spek.api.Spek
import org.kitteh.irc.client.library.element.User
import org.mockito.Matchers.anyString
import org.mockito.Matchers.eq
import org.mockito.Mockito.verify
import org.powermock.api.mockito.PowerMockito.*

class ExtensionsSpec : Spek({

    fun makeUser(nick: String): User {
        val user = mock(User::class.java)
        `when`(user.nick).thenReturn(nick)
        doNothing().`when`(user).sendMessage(anyString())
        return user
    }

    given("a mock User") {
        val user = makeUser("Dave")
        on("respond") {
            val message = "Hello!"
            user.respond(message)
            it("should ping the user") {
                verify(user).sendMessage(eq("${user.nick}: $message"))
            }
        }
    }
})
