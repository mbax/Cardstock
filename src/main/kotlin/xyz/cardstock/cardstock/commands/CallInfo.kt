/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.commands

/**
 * This class contains information on the calling of any given command.
 */
public class CallInfo(public val label: String, public val usageType: CallInfo.UsageType) {

    /**
     * Usage of the command.
     */
    public enum class UsageType {
        /**
         * Command was used in a channel message.
         */
        CHANNEL,
        /**
         * Command was used in a private message.
         */
        PRIVATE
    }
}
