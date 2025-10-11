/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.ide.colors

import consulo.colorScheme.TextAttributesKey
import consulo.colorScheme.setting.AttributesDescriptor
import consulo.configurable.OptionsBundle
import consulo.configurable.localize.ConfigurableLocalize
import consulo.language.editor.annotation.HighlightSeverity
import consulo.localize.LocalizeValue
import consulo.toml.localize.TomlLocalize
import org.toml.TomlBundle
import java.util.function.Supplier
import consulo.codeEditor.DefaultLanguageHighlighterColors as Default

enum class TomlColor(humanName: LocalizeValue, default: TextAttributesKey? = null) {
    KEY(TomlLocalize.colorSettingsTomlKeys(), Default.KEYWORD),
    COMMENT(TomlLocalize.colorSettingsTomlComments(), Default.LINE_COMMENT),
    BOOLEAN(TomlLocalize.colorSettingsTomlBoolean(), Default.PREDEFINED_SYMBOL),
    NUMBER(ConfigurableLocalize.optionsLanguageDefaultsNumber(), Default.NUMBER),
    DATE(TomlLocalize.colorSettingsTomlDate(), Default.PREDEFINED_SYMBOL),
    STRING(ConfigurableLocalize.optionsLanguageDefaultsString(), Default.STRING),
    VALID_STRING_ESCAPE(ConfigurableLocalize.optionsLanguageDefaultsValidEscSeq(), Default.VALID_STRING_ESCAPE),
    INVALID_STRING_ESCAPE(ConfigurableLocalize.optionsLanguageDefaultsInvalidEscSeq(), Default.INVALID_STRING_ESCAPE),
    ;

    val textAttributesKey: TextAttributesKey = TextAttributesKey.of("org.toml.$name", default)
    val attributesDescriptor: AttributesDescriptor = AttributesDescriptor(humanName, textAttributesKey)
    val testSeverity: HighlightSeverity = HighlightSeverity(name, HighlightSeverity.INFORMATION.myVal)
}
