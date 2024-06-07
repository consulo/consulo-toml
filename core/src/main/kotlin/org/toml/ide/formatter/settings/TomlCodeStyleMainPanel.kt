/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.ide.formatter.settings

import consulo.language.codeStyle.CodeStyleSettings
import consulo.language.codeStyle.ui.setting.TabbedLanguageCodeStylePanel
import org.toml.lang.TomlLanguage

class TomlCodeStyleMainPanel(currentSettings: CodeStyleSettings, settings: CodeStyleSettings) :
    TabbedLanguageCodeStylePanel(TomlLanguage, currentSettings, settings) {

    override fun initTabs(settings: CodeStyleSettings) {
        addIndentOptionsTab(settings)
    }
}
