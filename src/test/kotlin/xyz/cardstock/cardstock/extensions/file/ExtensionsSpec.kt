/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package xyz.cardstock.cardstock.extensions.file

import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import org.powermock.api.mockito.PowerMockito.`when`
import org.powermock.api.mockito.PowerMockito.mock
import xyz.cardstock.cardstock.MavenSpek
import java.io.File

class ExtensionsSpec : MavenSpek() {

    private fun makeFile(exists: Boolean, parentExists: Boolean): File {
        val file = this.makeSingleFile(exists)
        val parentFile = this.makeSingleFile(parentExists)
        `when`(file.parentFile).thenReturn(parentFile)
        return file
    }

    private fun makeSingleFile(exists: Boolean): File {
        val file = mock(File::class.java)
        `when`(file.exists()).thenReturn(exists)
        `when`(file.mkdirs()).thenReturn(true)
        `when`(file.createNewFile()).thenReturn(true)
        return file
    }

    override fun test() {
        given("a mock File that exists") {
            val file = this@ExtensionsSpec.makeFile(true, true)
            on("creation") {
                file.create()
                it("should check if the file exists") {
                    verify(file).exists()
                }
                it("should do nothing else") {
                    verifyNoMoreInteractions(file)
                }
            }
        }
        given("a mock File that does not exist and has missing parents") {
            val file = this@ExtensionsSpec.makeFile(false, false)
            on("creation") {
                file.create()
                it("should check if the file exists") {
                    verify(file).exists()
                }
                it("should check if the parent file exists") {
                    verify(file.parentFile).exists()
                }
                it("should create the parent folders") {
                    verify(file.parentFile).mkdirs()
                }
                it("should create the file") {
                    verify(file).createNewFile()
                }
            }
        }
        given("a mock File that does not exist and has parents") {
            val file = this@ExtensionsSpec.makeFile(false, true)
            on("creation") {
                file.create()
                it("should check if the file exists") {
                    verify(file).exists()
                }
                it("should check if the parent file exists") {
                    verify(file.parentFile).exists()
                }
                it("should do nothing more with the parent file") {
                    verifyNoMoreInteractions(file.parentFile)
                }
                it("should create the file") {
                    verify(file).createNewFile()
                }
            }
        }
    }
}
