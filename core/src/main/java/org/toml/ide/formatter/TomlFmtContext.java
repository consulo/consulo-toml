/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.ide.formatter;

import consulo.language.codeStyle.CodeStyleSettings;
import consulo.language.codeStyle.CommonCodeStyleSettings;
import consulo.language.codeStyle.SpacingBuilder;
import jakarta.annotation.Nonnull;
import org.toml.ide.formatter.impl.SpacingHelper;
import org.toml.lang.TomlLanguage;

public final class TomlFmtContext {
    private final CommonCodeStyleSettings commonSettings;
    private final SpacingBuilder spacingBuilder;

    public TomlFmtContext(@Nonnull CommonCodeStyleSettings commonSettings, @Nonnull SpacingBuilder spacingBuilder) {
        this.commonSettings = commonSettings;
        this.spacingBuilder = spacingBuilder;
    }

    @Nonnull
    public CommonCodeStyleSettings getCommonSettings() {
        return commonSettings;
    }

    @Nonnull
    public SpacingBuilder getSpacingBuilder() {
        return spacingBuilder;
    }

    @Nonnull
    public static TomlFmtContext create(@Nonnull CodeStyleSettings settings) {
        CommonCodeStyleSettings commonSettings = settings.getCommonSettings(TomlLanguage.INSTANCE);
        return new TomlFmtContext(commonSettings, SpacingHelper.createSpacingBuilder(commonSettings));
    }
}
