/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.lang.psi.impl;

import consulo.language.ast.IElementType;
import consulo.language.impl.psi.CompositePsiElement;
import consulo.language.psi.PsiElementVisitor;
import consulo.language.psi.util.PsiTreeUtil;
import jakarta.annotation.Nullable;
import jakarta.annotation.Nonnull;
import org.toml.lang.psi.TomlKey;
import org.toml.lang.psi.TomlTableHeader;
import org.toml.lang.psi.TomlVisitor;

public class TomlTableHeaderImpl extends CompositePsiElement implements TomlTableHeader {
    public TomlTableHeaderImpl(IElementType type) {
        super(type);
    }

    @Nullable
    @Override
    public TomlKey getKey() {
        return PsiTreeUtil.getChildOfType(this, TomlKey.class);
    }

    @Override
    public String toString() {
        return "TomlTableHeader";
    }

    @Override
    public void accept(@Nonnull PsiElementVisitor visitor) {
        if (visitor instanceof TomlVisitor) {
            ((TomlVisitor) visitor).visitTableHeader(this);
        } else {
            super.accept(visitor);
        }
    }
}
