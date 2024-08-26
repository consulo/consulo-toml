/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.ide.formatter.settings

import consulo.annotation.component.ExtensionImpl
import consulo.configurable.Configurable
import consulo.language.Language
import consulo.language.codeStyle.CodeStyleSettings
import consulo.language.codeStyle.CustomCodeStyleSettings
import consulo.language.codeStyle.setting.IndentOptionsEditor
import consulo.language.codeStyle.setting.LanguageCodeStyleSettingsProvider
import consulo.language.codeStyle.ui.setting.CodeStyleAbstractConfigurable
import consulo.language.codeStyle.ui.setting.CodeStyleAbstractPanel
import consulo.language.codeStyle.ui.setting.SmartIndentOptionsEditor
import consulo.util.lang.StringUtil
import org.toml.lang.TomlLanguage

@ExtensionImpl
class TomlLanguageCodeStyleSettingsProvider : LanguageCodeStyleSettingsProvider() {
    override fun getLanguage(): Language = TomlLanguage

    override fun createCustomSettings(settings: CodeStyleSettings): CustomCodeStyleSettings =
        TomlCodeStyleSettings(settings)

    override fun createSettingsPage(settings: CodeStyleSettings?, modelSettings: CodeStyleSettings?): Configurable {
        return object : CodeStyleAbstractConfigurable(settings, modelSettings, configurableDisplayName) {
            override fun createPanel(settings: CodeStyleSettings): CodeStyleAbstractPanel =
                TomlCodeStyleMainPanel(currentSettings, settings)
        }
    }

    override fun getCodeSample(settingsType: SettingsType): String =
        when (settingsType) {
            SettingsType.INDENT_SETTINGS -> INDENT_SAMPLE
            else -> ""
        }

    override fun getIndentOptionsEditor(): IndentOptionsEditor = SmartIndentOptionsEditor()
}

private fun sample(@org.intellij.lang.annotations.Language("TOML") code: String) = code.trim()

private val INDENT_SAMPLE = sample("""
[config]
items = [
    "foo",
    "bar"
]
""")
