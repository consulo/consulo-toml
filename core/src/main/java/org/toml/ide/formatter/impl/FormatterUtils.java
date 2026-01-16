/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.ide.formatter.impl;

import consulo.language.ast.ASTNode;
import consulo.language.ast.TokenSet;
import consulo.language.ast.TokenType;
import jakarta.annotation.Nullable;
import org.toml.lang.psi.TomlElementTypes;

public final class FormatterUtils {
    private FormatterUtils() {
    }

    public static final TokenSet ARRAY_DELIMITERS = TokenSet.create(TomlElementTypes.L_BRACKET, TomlElementTypes.R_BRACKET);

    public static boolean isWhitespaceOrEmpty(@Nullable ASTNode node) {
        return node == null || node.getTextLength() == 0 || node.getElementType() == TokenType.WHITE_SPACE;
    }

    public static boolean isArrayDelimiter(ASTNode node) {
        return ARRAY_DELIMITERS.contains(node.getElementType());
    }
}
