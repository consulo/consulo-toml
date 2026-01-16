/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.ide.formatter.impl;

import consulo.language.ast.TokenSet;
import consulo.language.codeStyle.Block;
import consulo.language.codeStyle.CommonCodeStyleSettings;
import consulo.language.codeStyle.Spacing;
import consulo.language.codeStyle.SpacingBuilder;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.toml.ide.formatter.TomlFmtContext;
import org.toml.lang.psi.TomlElementTypes;

public final class SpacingHelper {
    private SpacingHelper() {
    }

    @Nonnull
    public static SpacingBuilder createSpacingBuilder(@Nonnull CommonCodeStyleSettings commonSettings) {
        TokenSet keyTableHeader = TokenSet.create(TomlElementTypes.KEY, TomlElementTypes.TABLE_HEADER);

        return new SpacingBuilder(commonSettings)
                // ,
                .after(TomlElementTypes.COMMA).spacing(1, 1, 0, true, 0)
                .before(TomlElementTypes.COMMA).spaceIf(false)
                // =
                .around(TomlElementTypes.EQ).spacing(1, 1, 0, true, 0)
                // [ ]
                .after(TomlElementTypes.L_BRACKET).spaceIf(false)
                .before(TomlElementTypes.R_BRACKET).spaceIf(false)
                // { }
                .after(TomlElementTypes.L_CURLY).spaceIf(true)
                .before(TomlElementTypes.R_CURLY).spaceIf(true)
                // .
                .aroundInside(TomlElementTypes.DOT, keyTableHeader).spaceIf(false);
    }

    @Nullable
    public static Spacing computeSpacing(@Nonnull Block parentBlock, @Nullable Block child1, @Nonnull Block child2, @Nonnull TomlFmtContext ctx) {
        return ctx.getSpacingBuilder().getSpacing(parentBlock, child1, child2);
    }
}
