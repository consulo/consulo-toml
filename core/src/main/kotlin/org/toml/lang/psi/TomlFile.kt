/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.lang.psi

import consulo.language.file.FileViewProvider
import consulo.language.impl.psi.PsiFileBase
import consulo.virtualFileSystem.fileType.FileType
import org.toml.lang.TomlLanguage

class TomlFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, TomlLanguage) {
    override fun getFileType(): FileType = TomlFileType

    override fun toString(): String = "TOML File"
}
