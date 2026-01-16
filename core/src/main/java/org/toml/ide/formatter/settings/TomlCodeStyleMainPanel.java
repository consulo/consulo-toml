/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.ide.formatter.settings;

import consulo.language.codeStyle.CodeStyleSettings;
import consulo.language.codeStyle.ui.setting.TabbedLanguageCodeStylePanel;
import jakarta.annotation.Nonnull;
import org.toml.lang.TomlLanguage;

public class TomlCodeStyleMainPanel extends TabbedLanguageCodeStylePanel {
    public TomlCodeStyleMainPanel(@Nonnull CodeStyleSettings currentSettings, @Nonnull CodeStyleSettings settings) {
        super(TomlLanguage.INSTANCE, currentSettings, settings);
    }

    @Override
    protected void initTabs(@Nonnull CodeStyleSettings settings) {
        addIndentOptionsTab(settings);
    }
}
