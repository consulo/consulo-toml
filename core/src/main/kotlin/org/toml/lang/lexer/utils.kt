/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.lang.lexer

import consulo.language.ast.IElementType
import consulo.language.lexer.Lexer

fun CharSequence.tokenize(lexer: Lexer): Sequence<Pair<IElementType, String>> =
    generateSequence({
        lexer.start(this)
        lexer.tokenType?.to(lexer.tokenText)
    }, {
        lexer.advance()
        lexer.tokenType?.to(lexer.tokenText)
    })
