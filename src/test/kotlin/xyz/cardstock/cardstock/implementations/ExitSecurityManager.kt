/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.implementations

import java.security.Permission

class ExitSecurityManager : SecurityManager() {

    override fun checkExit(status: Int) = throw ExitException(status)

    override fun checkPermission(perm: Permission?, context: Any?) {
    }

    override fun checkPermission(perm: Permission?) {
    }

    class ExitException(val status: Int) : SecurityException()

}
