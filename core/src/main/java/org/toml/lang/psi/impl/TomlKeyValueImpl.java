/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.lang.psi.impl;

import consulo.language.ast.IElementType;
import consulo.language.impl.psi.CompositePsiElement;
import consulo.language.psi.PsiElementVisitor;
import consulo.language.psi.util.PsiTreeUtil;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.toml.lang.psi.*;

public class TomlKeyValueImpl extends CompositePsiElement implements TomlKeyValue {
    public TomlKeyValueImpl(IElementType type) {
        super(type);
    }

    @Nonnull
    @Override
    public TomlKey getKey() {
        TomlKey key = PsiTreeUtil.getChildOfType(this, TomlKey.class);
        if (key == null) {
            throw new IllegalStateException("Invalid TOML PSI: Expected to find TomlKey child of TomlKeyValue\nElement text:\n" + getText());
        }
        return key;
    }

    @Nullable
    @Override
    public TomlValue getValue() {
        return PsiTreeUtil.getChildOfType(this, TomlValue.class);
    }

    @Override
    public String toString() {
        return "TomlKeyValue";
    }

    @Override
    public void accept(@Nonnull PsiElementVisitor visitor) {
        if (visitor instanceof TomlVisitor) {
            ((TomlVisitor) visitor).visitKeyValue(this);
        } else {
            super.accept(visitor);
        }
    }
}
