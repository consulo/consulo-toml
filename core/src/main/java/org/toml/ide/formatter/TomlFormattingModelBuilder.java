/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.ide.formatter;

import consulo.annotation.component.ExtensionImpl;
import consulo.document.util.TextRange;
import consulo.language.Language;
import consulo.language.ast.ASTNode;
import consulo.language.codeStyle.*;
import consulo.language.psi.PsiFile;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.toml.lang.TomlLanguage;

@ExtensionImpl
public class TomlFormattingModelBuilder implements FormattingModelBuilder {
    @Nullable
    @Override
    public TextRange getRangeAffectingIndent(PsiFile file, int offset, ASTNode elementAtOffset) {
        return null;
    }

    @Nonnull
    @Override
    public FormattingModel createModel(@Nonnull FormattingContext formattingContext) {
        CodeStyleSettings settings = formattingContext.getCodeStyleSettings();
        var element = formattingContext.getPsiElement();
        TomlFmtContext ctx = TomlFmtContext.create(settings);
        Block block = createBlock(element.getNode(), null, Indent.getNoneIndent(), null, ctx);
        return FormattingModelProvider.createFormattingModelForPsiFile(element.getContainingFile(), block, settings);
    }

    @Nonnull
    @Override
    public Language getLanguage() {
        return TomlLanguage.INSTANCE;
    }

    @Nonnull
    public static ASTBlock createBlock(@Nonnull ASTNode node, @Nullable Alignment alignment, @Nullable Indent indent, @Nullable Wrap wrap, @Nonnull TomlFmtContext ctx) {
        return new TomlFmtBlock(node, alignment, indent, wrap, ctx);
    }
}
