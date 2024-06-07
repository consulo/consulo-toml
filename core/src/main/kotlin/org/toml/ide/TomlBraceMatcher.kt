/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.ide

import consulo.language.BracePair
import consulo.language.PairedBraceMatcher
import consulo.language.ast.IElementType
import consulo.language.psi.PsiFile
import org.toml.lang.TomlLanguage
import org.toml.lang.psi.TomlElementTypes.*

abstract class TomlBraceMatcher : PairedBraceMatcher {
    override fun getPairs() = PAIRS

    override fun isPairedBracesAllowedBeforeType(lbraceType: IElementType, contextType: IElementType?): Boolean = true

    override fun getCodeConstructStart(file: PsiFile, openingBraceOffset: Int): Int
        = openingBraceOffset

    companion object {
        private val PAIRS: Array<BracePair> = arrayOf(
            // Grammar Kit hack - ignore braces in recovery
            BracePair(IElementType("FAKE_L_BRACE", TomlLanguage), IElementType("FAKE_L_BRACE", TomlLanguage), false),

            BracePair(L_CURLY, R_CURLY, false),
            BracePair(L_BRACKET, R_BRACKET, false)
        )
    }
}

