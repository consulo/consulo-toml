/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.ide

import consulo.annotation.component.ExtensionImpl
import consulo.language.Language
import consulo.language.editor.action.LanguageQuoteHandler
import consulo.language.editor.action.SimpleTokenSetQuoteHandler
import org.toml.lang.TomlLanguage
import org.toml.lang.psi.TomlElementTypes

@ExtensionImpl
class TomlQuoteHandler : SimpleTokenSetQuoteHandler(TomlElementTypes.BASIC_STRING, TomlElementTypes.LITERAL_STRING), LanguageQuoteHandler {
    override fun getLanguage(): Language {
        return TomlLanguage
    }
}
