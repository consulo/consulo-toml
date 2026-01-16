/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.lang.lexer;

import consulo.language.ast.IElementType;
import jakarta.annotation.Nonnull;
import org.toml.lang.psi.TomlTokenSets;

public final class TomlEscapeUtils {
    private TomlEscapeUtils() {
    }

    @Nonnull
    public static String unescapeToml(@Nonnull String str, @Nonnull IElementType tokenType) {
        if (!TomlTokenSets.TOML_STRING_LITERALS.contains(tokenType)) {
            throw new IllegalArgumentException("Expected string literal token type");
        }

        String unNewlined;
        if (TomlTokenSets.TOML_MULTILINE_STRINGS.contains(tokenType)) {
            unNewlined = str.startsWith("\n") ? str.substring(1) : str;
        } else {
            unNewlined = str;
        }

        if (TomlTokenSets.TOML_BASIC_STRINGS.contains(tokenType)) {
            StringBuilder outChars = new StringBuilder();
            ParseResult result = parseTomlStringCharacters(tokenType, unNewlined, outChars);
            if (result.success) {
                return outChars.toString();
            }
            return unNewlined;
        }
        return unNewlined;
    }

    @Nonnull
    public static ParseResult parseTomlStringCharacters(
            @Nonnull IElementType tokenType,
            @Nonnull String chars,
            @Nonnull StringBuilder outChars) {
        if (!TomlTokenSets.TOML_BASIC_STRINGS.contains(tokenType)) {
            throw new IllegalArgumentException("Expected basic string token type");
        }
        int[] sourceOffsets = new int[chars.length() + 1];
        TomlEscapeLexer lexer = TomlEscapeLexer.of(tokenType);
        boolean result = EscapeUtils.parseStringCharacters(lexer, chars, outChars, sourceOffsets, TomlEscapeUtils::decodeEscape);
        return new ParseResult(sourceOffsets, result);
    }

    /**
     * See https://github.com/toml-lang/toml/blob/master/README.md#string
     */
    @Nonnull
    private static String decodeEscape(@Nonnull String esc) {
        if (esc.equals("\\b")) return "\b";
        if (esc.equals("\\t")) return "\t";
        if (esc.equals("\\n")) return "\n";
        if (esc.equals("\\f")) return "\u000C";
        if (esc.equals("\\r")) return "\r";
        if (esc.equals("\\\"")) return "\"";
        if (esc.equals("\\\\")) return "\\";

        if (esc.length() >= 2 && esc.charAt(0) == '\\') {
            char second = esc.charAt(1);
            if (second == 'u' || second == 'U') {
                int value = Integer.parseInt(esc.substring(2), 16);
                return String.valueOf((char) value);
            }
            if (second == '\r' || second == '\n') {
                return "";
            }
        }
        throw new IllegalStateException("unreachable");
    }

    public static class ParseResult {
        public final int[] sourceOffsets;
        public final boolean success;

        public ParseResult(int[] sourceOffsets, boolean success) {
            this.sourceOffsets = sourceOffsets;
            this.success = success;
        }
    }
}
