/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.lang.psi.ext;

import consulo.language.ast.ASTNode;
import consulo.language.ast.IElementType;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.toml.lang.lexer.TomlEscapeUtils;
import org.toml.lang.psi.LiteralOffsets;
import org.toml.lang.psi.TomlElementTypes;
import org.toml.lang.psi.TomlTokenSets;

public abstract class TomlLiteralKind {
    protected final ASTNode node;

    protected TomlLiteralKind(ASTNode node) {
        this.node = node;
    }

    @Nonnull
    public ASTNode getNode() {
        return node;
    }

    public static class BooleanKind extends TomlLiteralKind {
        public BooleanKind(ASTNode node) {
            super(node);
        }
    }

    public static class NumberKind extends TomlLiteralKind {
        public NumberKind(ASTNode node) {
            super(node);
        }
    }

    public static class DateTimeKind extends TomlLiteralKind {
        public DateTimeKind(ASTNode node) {
            super(node);
        }
    }

    public static class StringKind extends TomlLiteralKind {
        private LiteralOffsets offsets;

        public StringKind(ASTNode node) {
            super(node);
        }

        @Nullable
        public String getValue() {
            LiteralOffsets offsets = getOffsets();
            if (offsets.getValue() == null) {
                return null;
            }
            String substring = offsets.getValue().substring(node.getText());
            return TomlEscapeUtils.unescapeToml(substring, node.getElementType());
        }

        @Nonnull
        public LiteralOffsets getOffsets() {
            if (offsets == null) {
                offsets = TomlLiteralKindUtil.offsetsForTomlText(node);
            }
            return offsets;
        }
    }

    @Nullable
    public static TomlLiteralKind fromAstNode(@Nonnull ASTNode node) {
        IElementType elementType = node.getElementType();
        if (elementType == TomlElementTypes.BOOLEAN) {
            return new BooleanKind(node);
        }
        if (elementType == TomlElementTypes.NUMBER) {
            return new NumberKind(node);
        }
        if (elementType == TomlElementTypes.DATE_TIME) {
            return new DateTimeKind(node);
        }
        if (TomlTokenSets.TOML_STRING_LITERALS.contains(elementType)) {
            return new StringKind(node);
        }
        return null;
    }
}
