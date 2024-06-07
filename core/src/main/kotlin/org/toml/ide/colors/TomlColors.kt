/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.ide.colors

import consulo.colorScheme.TextAttributesKey
import consulo.colorScheme.setting.AttributesDescriptor
import consulo.configurable.OptionsBundle
import consulo.language.editor.annotation.HighlightSeverity
import org.toml.TomlBundle
import java.util.function.Supplier
import consulo.codeEditor.DefaultLanguageHighlighterColors as Default

enum class TomlColor(humanName: String, default: TextAttributesKey? = null) {
    KEY(TomlBundle.message("color.settings.toml.keys"), Default.KEYWORD),
    COMMENT(TomlBundle.message("color.settings.toml.comments"), Default.LINE_COMMENT),
    BOOLEAN(TomlBundle.message("color.settings.toml.boolean"), Default.PREDEFINED_SYMBOL),
    NUMBER(OptionsBundle.message("options.language.defaults.number"), Default.NUMBER),
    DATE(TomlBundle.message("color.settings.toml.date"), Default.PREDEFINED_SYMBOL),
    STRING(OptionsBundle.message("options.language.defaults.string"), Default.STRING),
    VALID_STRING_ESCAPE(OptionsBundle.message("options.language.defaults.valid.esc.seq"), Default.VALID_STRING_ESCAPE),
    INVALID_STRING_ESCAPE(OptionsBundle.message("options.language.defaults.invalid.esc.seq"), Default.INVALID_STRING_ESCAPE),
    ;

    val textAttributesKey: TextAttributesKey = TextAttributesKey.createTextAttributesKey("org.toml.$name", default)
    val attributesDescriptor: AttributesDescriptor = AttributesDescriptor(humanName, textAttributesKey)
    val testSeverity: HighlightSeverity = HighlightSeverity(name, HighlightSeverity.INFORMATION.myVal)
}
