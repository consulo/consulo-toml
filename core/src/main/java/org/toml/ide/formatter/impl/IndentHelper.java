/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.ide.formatter.impl;

import consulo.language.ast.ASTNode;
import consulo.language.ast.IElementType;
import consulo.language.codeStyle.Indent;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.toml.lang.psi.TomlElementTypes;

public final class IndentHelper {
    private IndentHelper() {
    }

    @Nullable
    public static Indent computeIndent(@Nonnull ASTNode parentNode, @Nonnull ASTNode child) {
        IElementType parentType = parentNode.getElementType();
        if (parentType == TomlElementTypes.ARRAY) {
            return getArrayIndent(child);
        }
        return Indent.getNoneIndent();
    }

    @Nonnull
    private static Indent getArrayIndent(@Nonnull ASTNode node) {
        if (FormatterUtils.isArrayDelimiter(node)) {
            return Indent.getNoneIndent();
        }
        return Indent.getNormalIndent();
    }
}
