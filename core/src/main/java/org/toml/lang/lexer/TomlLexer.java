/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.lang.lexer;

import consulo.language.lexer.FlexAdapter;

public class TomlLexer extends FlexAdapter {
    public TomlLexer() {
        super(new _TomlLexer(null));
    }
}
