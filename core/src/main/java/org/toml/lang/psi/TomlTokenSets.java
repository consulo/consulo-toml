/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.lang.psi;

import consulo.language.ast.TokenSet;

import static org.toml.lang.psi.TomlElementTypes.*;

public final class TomlTokenSets {
    private TomlTokenSets() {
    }

    public static final TokenSet TOML_COMMENTS = TokenSet.create(COMMENT);

    public static final TokenSet TOML_BASIC_STRINGS = TokenSet.create(BASIC_STRING, MULTILINE_BASIC_STRING);
    public static final TokenSet TOML_LITERAL_STRINGS = TokenSet.create(LITERAL_STRING, MULTILINE_LITERAL_STRING);
    public static final TokenSet TOML_SINGLE_LINE_STRINGS = TokenSet.create(BASIC_STRING, LITERAL_STRING);
    public static final TokenSet TOML_MULTILINE_STRINGS = TokenSet.create(MULTILINE_BASIC_STRING, MULTILINE_LITERAL_STRING);
    public static final TokenSet TOML_STRING_LITERALS = TokenSet.orSet(TOML_BASIC_STRINGS, TOML_LITERAL_STRINGS);
    public static final TokenSet TOML_LITERALS = TokenSet.orSet(
            TOML_STRING_LITERALS,
            TokenSet.create(BOOLEAN, NUMBER, DATE_TIME)
    );
}
