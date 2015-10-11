/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock

import xyz.cardstock.cardstock.implementations.DummyCardstock
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertEquals

class CardstockSpec : MavenSpek() {
    override fun test() {
        val cardstock = DummyCardstock()
        given("a Cardstock instance") {
            on("setting up and invoking the logger") {
                // Prepare to catch the output
                val output = ByteArrayOutputStream()
                val printStream = PrintStream(output)
                val oldErr = System.err
                System.setErr(printStream)
                // Set up the logger
                cardstock.setUpLogger()
                it("should output in the expected format") {
                    val string = "Testing 123"
                    cardstock.logger.info(string)
                    assertEquals("[INFO] $string\n", output.toString())
                }
                // Restore output
                System.setErr(oldErr)
            }
        }
    }
}
