/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.lang.lexer;

import consulo.language.ast.IElementType;
import consulo.language.lexer.LayeredLexer;

public class TomlHighlightingLexer extends LayeredLexer {
    public TomlHighlightingLexer() {
        super(new TomlLexer());
        for (IElementType tokenType : TomlEscapeLexer.ESCAPABLE_LITERALS_TOKEN_SET.getTypes()) {
            registerLayer(TomlEscapeLexer.of(tokenType), tokenType);
        }
    }
}
