/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.lang;

import consulo.language.Language;

public class TomlLanguage extends Language {
    public static final TomlLanguage INSTANCE = new TomlLanguage();

    private TomlLanguage() {
        super("TOML", "text/toml");
    }

    @Override
    public boolean isCaseSensitive() {
        return true;
    }
}
