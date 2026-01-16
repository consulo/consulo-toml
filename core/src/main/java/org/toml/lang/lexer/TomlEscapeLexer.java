/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.lang.lexer;

import consulo.language.ast.IElementType;
import consulo.language.ast.StringEscapesTokenTypes;
import consulo.language.ast.TokenSet;
import consulo.util.lang.CharArrayUtil;
import consulo.util.lang.StringUtil;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.toml.lang.psi.TomlElementTypes;
import org.toml.lang.psi.TomlTokenSets;

public class TomlEscapeLexer extends LexerBaseEx {
    private final IElementType defaultToken;
    private final boolean eol;

    private TomlEscapeLexer(IElementType defaultToken, boolean eol) {
        this.defaultToken = defaultToken;
        this.eol = eol;
    }

    @Nullable
    @Override
    protected IElementType determineTokenType() {
        // We're at the end of the string token => finish lexing
        if (tokenStart >= tokenEnd) {
            return null;
        }

        // We're not inside escape sequence
        if (bufferSequence.charAt(tokenStart) != '\\') {
            return defaultToken;
        }

        // \ is at the end of the string token
        if (tokenStart + 1 >= tokenEnd) {
            return StringEscapesTokenTypes.INVALID_CHARACTER_ESCAPE_TOKEN;
        }

        char nextChar = bufferSequence.charAt(tokenStart + 1);
        switch (nextChar) {
            case 'u':
            case 'U':
                return isValidUnicodeEscape(tokenStart, tokenEnd)
                        ? StringEscapesTokenTypes.VALID_STRING_ESCAPE_TOKEN
                        : StringEscapesTokenTypes.INVALID_UNICODE_ESCAPE_TOKEN;
            case '\r':
            case '\n':
                return EscapeUtils.esc(eol);
            case 'b':
            case 't':
            case 'n':
            case 'f':
            case 'r':
            case '"':
            case '\\':
                return StringEscapesTokenTypes.VALID_STRING_ESCAPE_TOKEN;
            default:
                return StringEscapesTokenTypes.INVALID_CHARACTER_ESCAPE_TOKEN;
        }
    }

    private boolean isValidUnicodeEscape(int start, int end) {
        try {
            int value = Integer.parseInt(bufferSequence.subSequence(start + 2, end).toString(), 16);
            // https://github.com/toml-lang/toml/blame/92a0a60bf37093fe0a888e5c98445e707ac9375b/README.md#L296-L297
            // https://unicode.org/glossary/#unicode_scalar_value
            return (value >= 0 && value <= 0xD7FF) || (value >= 0xE000 && value <= 0x10FFFF);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    protected int locateToken(int start) {
        if (start >= bufferEnd) {
            return start;
        }

        if (bufferSequence.charAt(start) == '\\') {
            int i = start + 1;

            if (i >= bufferEnd) {
                return bufferEnd;
            }

            char ch = bufferSequence.charAt(i);
            switch (ch) {
                case 'u':
                    return unicodeTokenEnd(i + 1, 4);
                case 'U':
                    return unicodeTokenEnd(i + 1, 8);
                case '\r':
                case '\n':
                    int j = i;
                    while (j < bufferEnd && EscapeUtils.isWhitespaceChar(bufferSequence.charAt(j))) {
                        j++;
                    }
                    return j;
                default:
                    return i + 1;
            }
        } else {
            int idx = CharArrayUtil.indexOf(bufferSequence, "\\", start + 1, bufferEnd);
            return idx != -1 ? idx : bufferEnd;
        }
    }

    private int unicodeTokenEnd(int start, int expectedLength) {
        for (int i = start; i < start + expectedLength; i++) {
            if (i >= bufferEnd || !StringUtil.isHexDigit(bufferSequence.charAt(i))) {
                return start;
            }
        }
        return start + expectedLength;
    }

    @Nonnull
    public static TomlEscapeLexer of(@Nonnull IElementType tokenType) {
        if (tokenType == TomlElementTypes.BASIC_STRING) {
            return new TomlEscapeLexer(TomlElementTypes.BASIC_STRING, false);
        } else if (tokenType == TomlElementTypes.MULTILINE_BASIC_STRING) {
            return new TomlEscapeLexer(TomlElementTypes.MULTILINE_BASIC_STRING, true);
        } else {
            throw new IllegalArgumentException("Unsupported literal type: " + tokenType);
        }
    }

    /**
     * Set of possible arguments for of()
     */
    public static final TokenSet ESCAPABLE_LITERALS_TOKEN_SET = TomlTokenSets.TOML_BASIC_STRINGS;
}
