/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.ide

import consulo.annotation.component.ExtensionImpl
import consulo.language.Commenter
import consulo.language.Language
import org.toml.lang.TomlLanguage

@ExtensionImpl
class TomlCommenter : Commenter {
    override fun getLineCommentPrefix(): String = "#"

    override fun getBlockCommentPrefix(): String? = null
    override fun getBlockCommentSuffix(): String? = null

    override fun getCommentedBlockCommentPrefix(): String? = null
    override fun getCommentedBlockCommentSuffix(): String? = null
    override fun getLanguage(): Language = TomlLanguage
}
