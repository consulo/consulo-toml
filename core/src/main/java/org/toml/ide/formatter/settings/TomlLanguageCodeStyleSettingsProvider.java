/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.ide.formatter.settings;

import consulo.annotation.component.ExtensionImpl;
import consulo.configurable.Configurable;
import consulo.language.Language;
import consulo.language.codeStyle.CodeStyleSettings;
import consulo.language.codeStyle.CustomCodeStyleSettings;
import consulo.language.codeStyle.setting.IndentOptionsEditor;
import consulo.language.codeStyle.setting.LanguageCodeStyleSettingsProvider;
import consulo.language.codeStyle.ui.setting.CodeStyleAbstractConfigurable;
import consulo.language.codeStyle.ui.setting.CodeStyleAbstractPanel;
import consulo.language.codeStyle.ui.setting.SmartIndentOptionsEditor;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.toml.lang.TomlLanguage;

@ExtensionImpl
public class TomlLanguageCodeStyleSettingsProvider extends LanguageCodeStyleSettingsProvider {
    private static final String INDENT_SAMPLE = "[config]\n" +
            "items = [\n" +
            "    \"foo\",\n" +
            "    \"bar\"\n" +
            "]";

    @Nonnull
    @Override
    public Language getLanguage() {
        return TomlLanguage.INSTANCE;
    }

    @Nonnull
    @Override
    public CustomCodeStyleSettings createCustomSettings(@Nonnull CodeStyleSettings settings) {
        return new TomlCodeStyleSettings(settings);
    }

    @Nonnull
    @Override
    public Configurable createSettingsPage(@Nullable CodeStyleSettings settings, @Nullable CodeStyleSettings modelSettings) {
        return new CodeStyleAbstractConfigurable(settings, modelSettings, getConfigurableDisplayName()) {
            @Override
            protected CodeStyleAbstractPanel createPanel(CodeStyleSettings settings) {
                return new TomlCodeStyleMainPanel(getCurrentSettings(), settings);
            }
        };
    }

    @Nonnull
    @Override
    public String getCodeSample(@Nonnull SettingsType settingsType) {
        if (settingsType == SettingsType.INDENT_SETTINGS) {
            return INDENT_SAMPLE;
        }
        return "";
    }

    @Nullable
    @Override
    public IndentOptionsEditor getIndentOptionsEditor() {
        return new SmartIndentOptionsEditor();
    }
}
