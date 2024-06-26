/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.lang.parse

import consulo.language.ast.IElementType
import consulo.language.ast.TokenType
import consulo.language.impl.parser.GeneratedParserUtilBase
import consulo.language.parser.PsiBuilder

object TomlParserUtil : GeneratedParserUtilBase() {
    @Suppress("UNUSED_PARAMETER")
    @JvmStatic
    fun remap(b: PsiBuilder, level: Int, from: IElementType, to: IElementType): Boolean {
        if (b.tokenType == from) {
            b.remapCurrentToken(to)
            b.advanceLexer()
            return true
        }
        return false
    }

    @Suppress("UNUSED_PARAMETER")
    @JvmStatic
    fun any(b: PsiBuilder, level: Int): Boolean = true

    @JvmStatic
    fun atSameLine(b: PsiBuilder, level: Int, parser: Parser): Boolean {
        val marker = enter_section_(b)
        b.eof() // skip whitespace
        val isSameLine = !isNextAfterNewLine(b)
        if (!isSameLine) addVariant(b, "VALUE")
        val result = isSameLine && parser.parse(b, level)
        exit_section_(b, marker, null, result)
        return result
    }

    @JvmStatic
    fun atNewLine(b: PsiBuilder, level: Int, parser: Parser): Boolean {
        val marker = enter_section_(b)
        b.eof() // skip whitespace
        val result = isNextAfterNewLine(b) && parser.parse(b, level)
        exit_section_(b, marker, null, result)
        return result
    }
}

private fun isNextAfterNewLine(b: PsiBuilder): Boolean {
    return when (b.rawLookup(-1)) {
        null -> true
        // The previous white space token contains end of line, or it's the first white space in file
        TokenType.WHITE_SPACE -> b.rawLookupText(-1).contains("\n") || b.rawTokenIndex() == 1
        else -> false
    }
}


/** Similar to [com.intellij.lang.PsiBuilderUtil.rawTokenText] */
private fun PsiBuilder.rawLookupText(steps: Int): CharSequence {
  val start = rawTokenTypeStart(steps)
  val end = rawTokenTypeStart(steps + 1)
  return if (start == -1 || end == -1) "" else originalText.subSequence(start, end)
}
