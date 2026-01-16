/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.lang.psi.ext;

import consulo.language.ast.ASTNode;
import consulo.language.ast.IElementType;
import jakarta.annotation.Nonnull;
import org.toml.lang.psi.LiteralOffsets;
import org.toml.lang.psi.TomlElementTypes;

public final class TomlLiteralKindUtil {
    private TomlLiteralKindUtil() {
    }

    @Nonnull
    public static LiteralOffsets offsetsForTomlText(@Nonnull ASTNode node) {
        IElementType elementType = node.getElementType();
        String quote;
        boolean needEscape;

        if (elementType == TomlElementTypes.BASIC_STRING) {
            quote = "\"";
            needEscape = true;
        } else if (elementType == TomlElementTypes.MULTILINE_BASIC_STRING) {
            quote = "\"\"\"";
            needEscape = true;
        } else if (elementType == TomlElementTypes.LITERAL_STRING) {
            quote = "'";
            needEscape = false;
        } else if (elementType == TomlElementTypes.MULTILINE_LITERAL_STRING) {
            quote = "'''";
            needEscape = false;
        } else {
            throw new IllegalArgumentException("Unexpected element type: `" + elementType + "` for `" + node.getText() + "`");
        }

        String text = node.getText();
        int textLength = node.getTextLength();

        int openDelimEnd = doLocate(text, textLength, 0, t -> t.startsWith(quote) ? quote.length() : 0);

        int valueEnd = doLocate(text, textLength, openDelimEnd, t -> {
            boolean escape = false;
            for (int i = 0; i < t.length(); i++) {
                char ch = t.charAt(i);
                if (escape) {
                    escape = false;
                } else if (needEscape && ch == '\\') {
                    escape = true;
                } else if (t.substring(i).startsWith(quote)) {
                    return i;
                }
            }
            return t.length();
        });

        int closeDelimEnd = doLocate(text, textLength, valueEnd, t -> t.startsWith(quote) ? quote.length() : 0);

        return LiteralOffsets.fromEndOffsets(0, openDelimEnd, valueEnd, closeDelimEnd, 0);
    }

    private static int doLocate(String text, int textLength, int start, Locator locator) {
        if (start >= textLength) {
            return start;
        }
        return start + locator.locate(text.substring(start, textLength));
    }

    @FunctionalInterface
    private interface Locator {
        int locate(String text);
    }
}
