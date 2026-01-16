/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.ide.colors;

import consulo.codeEditor.DefaultLanguageHighlighterColors;
import consulo.colorScheme.TextAttributesKey;
import consulo.colorScheme.setting.AttributesDescriptor;
import consulo.configurable.localize.ConfigurableLocalize;
import consulo.language.editor.annotation.HighlightSeverity;
import consulo.localize.LocalizeValue;
import consulo.toml.localize.TomlLocalize;
import jakarta.annotation.Nonnull;

public enum TomlColor {
    KEY(TomlLocalize.colorSettingsTomlKeys(), DefaultLanguageHighlighterColors.KEYWORD),
    COMMENT(TomlLocalize.colorSettingsTomlComments(), DefaultLanguageHighlighterColors.LINE_COMMENT),
    BOOLEAN(TomlLocalize.colorSettingsTomlBoolean(), DefaultLanguageHighlighterColors.PREDEFINED_SYMBOL),
    DATE(TomlLocalize.colorSettingsTomlDate(), DefaultLanguageHighlighterColors.PREDEFINED_SYMBOL),
    NUMBER(ConfigurableLocalize.optionsLanguageDefaultsNumber(), DefaultLanguageHighlighterColors.NUMBER),
    STRING(ConfigurableLocalize.optionsLanguageDefaultsString(), DefaultLanguageHighlighterColors.STRING),
    VALID_STRING_ESCAPE(ConfigurableLocalize.optionsLanguageDefaultsValidEscSeq(), DefaultLanguageHighlighterColors.VALID_STRING_ESCAPE),
    INVALID_STRING_ESCAPE(ConfigurableLocalize.optionsLanguageDefaultsInvalidEscSeq(), DefaultLanguageHighlighterColors.INVALID_STRING_ESCAPE);

    private final TextAttributesKey textAttributesKey;
    private final AttributesDescriptor attributesDescriptor;
    private final HighlightSeverity testSeverity;

    TomlColor(@Nonnull LocalizeValue humanName, TextAttributesKey defaultKey) {
        this.textAttributesKey = TextAttributesKey.of("org.toml." + name(), defaultKey);
        this.attributesDescriptor = new AttributesDescriptor(humanName, textAttributesKey);
        this.testSeverity = new HighlightSeverity(name(), HighlightSeverity.INFORMATION.myVal);
    }

    @Nonnull
    public TextAttributesKey getTextAttributesKey() {
        return textAttributesKey;
    }

    @Nonnull
    public AttributesDescriptor getAttributesDescriptor() {
        return attributesDescriptor;
    }

    @Nonnull
    public HighlightSeverity getTestSeverity() {
        return testSeverity;
    }
}
