/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.lang.psi;

import consulo.language.ast.ASTNode;
import jakarta.annotation.Nonnull;
import org.toml.lang.lexer.TomlEscapeUtils;

public class TomlLiteralTextEscaper extends LiteralTextEscaperBase<TomlLiteral> {

    public TomlLiteralTextEscaper(@Nonnull TomlLiteral host) {
        super(host);
    }

    @Nonnull
    @Override
    protected ParseResult parseStringCharacters(@Nonnull String chars, @Nonnull StringBuilder outChars) {
        ASTNode child = myHost.getNode().findChildByType(TomlTokenSets.TOML_BASIC_STRINGS);
        if (child == null) {
            throw new IllegalStateException("Failed to find basic string child");
        }
        TomlEscapeUtils.ParseResult result = TomlEscapeUtils.parseTomlStringCharacters(child.getElementType(), chars, outChars);
        return new ParseResult(result.sourceOffsets, result.success);
    }

    @Override
    public boolean isOneLine() {
        return false;
    }
}
