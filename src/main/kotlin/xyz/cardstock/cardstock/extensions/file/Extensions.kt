/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.extensions.file

import java.io.File

/**
 * Creates the file if it does not exist. If the parent folders do not exist, they will be created. If any creations
 * fail, this will throw an [IllegalStateException].
 * @throws IllegalStateException
 */
public fun File.create() {
    if (this.exists()) return
    if (!this.parentFile.exists()) check(this.parentFile.mkdirs())
    check(this.createNewFile())
}
