/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.ide.colors;

import consulo.annotation.component.ExtensionImpl;
import consulo.colorScheme.TextAttributesKey;
import consulo.colorScheme.setting.AttributesDescriptor;
import consulo.colorScheme.setting.ColorDescriptor;
import consulo.language.editor.colorScheme.setting.ColorSettingsPage;
import consulo.language.editor.highlight.SyntaxHighlighter;
import consulo.localize.LocalizeValue;
import consulo.util.io.StreamUtil;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.toml.ide.TomlHighlighter;
import org.toml.lang.TomlLanguage;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@ExtensionImpl
public class TomlColorSettingsPage implements ColorSettingsPage {
    private final AttributesDescriptor[] attributesDescriptors;
    private final Map<String, TextAttributesKey> tagToDescriptorMap;
    private String highlighterDemoText;

    public TomlColorSettingsPage() {
        TomlColor[] colors = TomlColor.values();
        attributesDescriptors = new AttributesDescriptor[colors.length];
        tagToDescriptorMap = new HashMap<>();
        for (int i = 0; i < colors.length; i++) {
            attributesDescriptors[i] = colors[i].getAttributesDescriptor();
            tagToDescriptorMap.put(colors[i].name(), colors[i].getTextAttributesKey());
        }
    }

    @Nonnull
    @Override
    public LocalizeValue getDisplayName() {
        return TomlLanguage.INSTANCE.getDisplayName();
    }

    @Nonnull
    @Override
    public SyntaxHighlighter getHighlighter() {
        return new TomlHighlighter();
    }

    @Nullable
    @Override
    public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
        return tagToDescriptorMap;
    }

    @Nonnull
    @Override
    public AttributesDescriptor[] getAttributeDescriptors() {
        return attributesDescriptors;
    }

    @Nonnull
    @Override
    public ColorDescriptor[] getColorDescriptors() {
        return ColorDescriptor.EMPTY_ARRAY;
    }

    @Nonnull
    @Override
    public String getDemoText() {
        if (highlighterDemoText == null) {
            try (InputStream stream = getClass().getClassLoader().getResourceAsStream("org/toml/ide/colors/highlighterDemoText.toml")) {
                if (stream != null) {
                    highlighterDemoText = StreamUtil.convertSeparators(StreamUtil.readText(stream, "UTF-8"));
                } else {
                    highlighterDemoText = "# Sample TOML\nkey = \"value\"";
                }
            } catch (IOException e) {
                highlighterDemoText = "# Sample TOML\nkey = \"value\"";
            }
        }
        return highlighterDemoText;
    }
}
