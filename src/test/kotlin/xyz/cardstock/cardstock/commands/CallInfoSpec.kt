/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.commands

import org.jetbrains.spek.api.Spek
import kotlin.test.assertTrue

class CallInfoSpec : Spek({
    given("an instance of CallInfo") {
        val label = "test"
        val usageType = CallInfo.UsageType.CHANNEL
        val callInfo = CallInfo(label, usageType)
        on("accessing label") {
            val callInfoLabel = callInfo.label
            it("should be the same as the value it was constructed with") {
                assertTrue(label === callInfoLabel)
            }
        }
        on("accessing usageType") {
            val callInfoUsageType = callInfo.usageType
            it("should be the same as the value it was constructed with") {
                assertTrue(usageType === callInfoUsageType)
            }
        }
    }
})
