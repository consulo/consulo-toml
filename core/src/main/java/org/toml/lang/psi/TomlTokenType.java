/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.lang.psi;

import consulo.language.ast.IElementType;
import org.toml.lang.TomlLanguage;

public class TomlTokenType extends IElementType {
    public TomlTokenType(String debugName) {
        super(debugName, TomlLanguage.INSTANCE);
    }
}
