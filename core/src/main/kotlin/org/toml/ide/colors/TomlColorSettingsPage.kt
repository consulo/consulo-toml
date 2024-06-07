/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.ide.colors

import consulo.colorScheme.TextAttributesKey
import consulo.colorScheme.setting.AttributesDescriptor
import consulo.colorScheme.setting.ColorDescriptor
import consulo.language.editor.colorScheme.setting.ColorSettingsPage
import consulo.language.editor.highlight.SyntaxHighlighter
import consulo.util.io.StreamUtil
import org.toml.ide.TomlHighlighter
import org.toml.lang.TomlLanguage

abstract class TomlColorSettingsPage : ColorSettingsPage {

    private val attributesDescriptors = TomlColor.values().map { it.attributesDescriptor }.toTypedArray()
    private val tagToDescriptorMap = TomlColor.values().associateBy({ it.name }, { it.textAttributesKey })
    private val highlighterDemoText by lazy {
        val stream = javaClass.classLoader.getResourceAsStream("org/toml/ide/colors/highlighterDemoText.toml")
        StreamUtil.convertSeparators(StreamUtil.readText(stream, "UTF-8"))
    }

    override fun getDisplayName(): String = TomlLanguage.displayName
    override fun getHighlighter(): SyntaxHighlighter = TomlHighlighter()
    override fun getAdditionalHighlightingTagToDescriptorMap(): Map<String, TextAttributesKey> = tagToDescriptorMap
    override fun getAttributeDescriptors(): Array<AttributesDescriptor> = attributesDescriptors
    override fun getColorDescriptors(): Array<ColorDescriptor> = ColorDescriptor.EMPTY_ARRAY
    override fun getDemoText(): String = highlighterDemoText
}
