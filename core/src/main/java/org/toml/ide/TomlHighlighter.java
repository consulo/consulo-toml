/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.ide;

import consulo.colorScheme.TextAttributesKey;
import consulo.language.ast.IElementType;
import consulo.language.ast.StringEscapesTokenTypes;
import consulo.language.editor.highlight.SyntaxHighlighterBase;
import consulo.language.lexer.Lexer;
import jakarta.annotation.Nonnull;
import org.toml.ide.colors.TomlColor;
import org.toml.lang.lexer.TomlHighlightingLexer;
import org.toml.lang.psi.TomlElementTypes;

import java.util.HashMap;
import java.util.Map;

public class TomlHighlighter extends SyntaxHighlighterBase {
    private static final Map<IElementType, TomlColor> TOKEN_MAP = new HashMap<>();

    static {
        TOKEN_MAP.put(TomlElementTypes.BARE_KEY, TomlColor.KEY);
        TOKEN_MAP.put(TomlElementTypes.COMMENT, TomlColor.COMMENT);
        TOKEN_MAP.put(TomlElementTypes.BASIC_STRING, TomlColor.STRING);
        TOKEN_MAP.put(TomlElementTypes.LITERAL_STRING, TomlColor.STRING);
        TOKEN_MAP.put(TomlElementTypes.MULTILINE_BASIC_STRING, TomlColor.STRING);
        TOKEN_MAP.put(TomlElementTypes.MULTILINE_LITERAL_STRING, TomlColor.STRING);
        TOKEN_MAP.put(TomlElementTypes.NUMBER, TomlColor.NUMBER);
        TOKEN_MAP.put(TomlElementTypes.BOOLEAN, TomlColor.BOOLEAN);
        TOKEN_MAP.put(TomlElementTypes.DATE_TIME, TomlColor.DATE);
        TOKEN_MAP.put(StringEscapesTokenTypes.INVALID_CHARACTER_ESCAPE_TOKEN, TomlColor.INVALID_STRING_ESCAPE);
        TOKEN_MAP.put(StringEscapesTokenTypes.INVALID_UNICODE_ESCAPE_TOKEN, TomlColor.INVALID_STRING_ESCAPE);
        TOKEN_MAP.put(StringEscapesTokenTypes.VALID_STRING_ESCAPE_TOKEN, TomlColor.VALID_STRING_ESCAPE);
    }

    @Nonnull
    @Override
    public Lexer getHighlightingLexer() {
        return new TomlHighlightingLexer();
    }

    @Nonnull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        TomlColor color = TOKEN_MAP.get(tokenType);
        return pack(color != null ? color.getTextAttributesKey() : null);
    }
}
