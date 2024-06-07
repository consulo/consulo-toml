/*
* Use of this source code is governed by the MIT license that can be
* found in the LICENSE file.
*/

package org.toml.ide.formatter.impl

import consulo.language.codeStyle.Block
import consulo.language.codeStyle.CommonCodeStyleSettings
import consulo.language.codeStyle.Spacing
import consulo.language.codeStyle.SpacingBuilder
import org.toml.ide.formatter.TomlFmtContext
import org.toml.lang.psi.TomlElementTypes.*
import consulo.language.ast.TokenSet.create as ts

fun createSpacingBuilder(commonSettings: CommonCodeStyleSettings): SpacingBuilder =
    SpacingBuilder(commonSettings)
        // ,
        .after(COMMA).spacing(1, 1, 0, true, 0)
        .before(COMMA).spaceIf(false)
        // =
        .around(EQ).spacing(1, 1, 0, true, 0)
        // [ ]
        .after(L_BRACKET).spaceIf(false)
        .before(R_BRACKET).spaceIf(false)
        // { }
        .after(L_CURLY).spaceIf(true)
        .before(R_CURLY).spaceIf(true)
        // .
        .aroundInside(DOT, ts(KEY, TABLE_HEADER)).spaceIf(false)

fun Block.computeSpacing(child1: Block?, child2: Block, ctx: TomlFmtContext): Spacing? {
    return ctx.spacingBuilder.getSpacing(this, child1, child2)
}
