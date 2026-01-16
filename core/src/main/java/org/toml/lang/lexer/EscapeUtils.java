/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.lang.lexer;

import consulo.language.ast.IElementType;
import consulo.language.ast.StringEscapesTokenTypes;
import consulo.language.lexer.Lexer;
import jakarta.annotation.Nonnull;

import java.util.function.Function;

public final class EscapeUtils {
    private EscapeUtils() {
    }

    @Nonnull
    public static IElementType esc(boolean test) {
        return test ? StringEscapesTokenTypes.VALID_STRING_ESCAPE_TOKEN : StringEscapesTokenTypes.INVALID_CHARACTER_ESCAPE_TOKEN;
    }

    /**
     * Determines if the char is a whitespace character.
     */
    public static boolean isWhitespaceChar(char ch) {
        return ch == ' ' || ch == '\r' || ch == '\n' || ch == '\t';
    }

    /**
     * Mimics com.intellij.codeInsight.CodeInsightUtilCore.parseStringCharacters
     * but obeys specific escaping rules provided by decoder.
     */
    public static boolean parseStringCharacters(
            @Nonnull Lexer lexer,
            @Nonnull String chars,
            @Nonnull StringBuilder outChars,
            @Nonnull int[] sourceOffsets,
            @Nonnull Function<String, String> decoder) {
        int outOffset = outChars.length();
        int index = 0;

        lexer.start(chars);
        while (lexer.getTokenType() != null) {
            IElementType type = lexer.getTokenType();
            String text = lexer.getTokenText();

            // Set offset for the decoded character to the beginning of the escape sequence.
            sourceOffsets[outChars.length() - outOffset] = index;
            sourceOffsets[outChars.length() - outOffset + 1] = index + 1;

            if (type == StringEscapesTokenTypes.VALID_STRING_ESCAPE_TOKEN) {
                outChars.append(decoder.apply(text));
                // And perform a "jump"
                index += text.length();
            } else if (type == StringEscapesTokenTypes.INVALID_CHARACTER_ESCAPE_TOKEN ||
                       type == StringEscapesTokenTypes.INVALID_UNICODE_ESCAPE_TOKEN) {
                return false;
            } else {
                int first = outChars.length() - outOffset;
                outChars.append(text);
                int last = outChars.length() - outOffset - 1;
                // Set offsets for each character of given chunk
                for (int i = first; i <= last; i++) {
                    sourceOffsets[i] = index;
                    index++;
                }
            }

            lexer.advance();
        }

        sourceOffsets[outChars.length() - outOffset] = index;
        return true;
    }
}
