/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.ide.formatter

import consulo.language.codeStyle.CodeStyleSettings
import consulo.language.codeStyle.CommonCodeStyleSettings
import consulo.language.codeStyle.SpacingBuilder
import org.toml.ide.formatter.impl.createSpacingBuilder
import org.toml.lang.TomlLanguage

data class TomlFmtContext(
    val commonSettings: CommonCodeStyleSettings,
    val spacingBuilder: SpacingBuilder
) {
    companion object {
        fun create(settings: CodeStyleSettings): TomlFmtContext {
            val commonSettings = settings.getCommonSettings(TomlLanguage)
            return TomlFmtContext(commonSettings, createSpacingBuilder(commonSettings))
        }
    }
}
