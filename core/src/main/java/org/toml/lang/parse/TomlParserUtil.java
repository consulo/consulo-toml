/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.lang.parse;

import consulo.language.ast.IElementType;
import consulo.language.ast.TokenType;
import consulo.language.impl.parser.GeneratedParserUtilBase;
import consulo.language.parser.PsiBuilder;

public class TomlParserUtil extends GeneratedParserUtilBase {

    @SuppressWarnings("unused")
    public static boolean remap(PsiBuilder b, int level, IElementType from, IElementType to) {
        if (b.getTokenType() == from) {
            b.remapCurrentToken(to);
            b.advanceLexer();
            return true;
        }
        return false;
    }

    @SuppressWarnings("unused")
    public static boolean any(PsiBuilder b, int level) {
        return true;
    }

    public static boolean atSameLine(PsiBuilder b, int level, Parser parser) {
        PsiBuilder.Marker marker = enter_section_(b);
        b.eof(); // skip whitespace
        boolean isSameLine = !isNextAfterNewLine(b);
        if (!isSameLine) {
            addVariant(b, "VALUE");
        }
        boolean result = isSameLine && parser.parse(b, level);
        exit_section_(b, marker, null, result);
        return result;
    }

    public static boolean atNewLine(PsiBuilder b, int level, Parser parser) {
        PsiBuilder.Marker marker = enter_section_(b);
        b.eof(); // skip whitespace
        boolean result = isNextAfterNewLine(b) && parser.parse(b, level);
        exit_section_(b, marker, null, result);
        return result;
    }

    private static boolean isNextAfterNewLine(PsiBuilder b) {
        IElementType prevType = b.rawLookup(-1);
        if (prevType == null) {
            return true;
        }
        // The previous white space token contains end of line, or it's the first white space in file
        if (prevType == TokenType.WHITE_SPACE) {
            CharSequence prevText = rawLookupText(b, -1);
            return prevText.toString().contains("\n") || b.rawTokenIndex() == 1;
        }
        return false;
    }

    /**
     * Similar to com.intellij.lang.PsiBuilderUtil.rawTokenText
     */
    private static CharSequence rawLookupText(PsiBuilder b, int steps) {
        int start = b.rawTokenTypeStart(steps);
        int end = b.rawTokenTypeStart(steps + 1);
        if (start == -1 || end == -1) {
            return "";
        }
        return b.getOriginalText().subSequence(start, end);
    }
}
