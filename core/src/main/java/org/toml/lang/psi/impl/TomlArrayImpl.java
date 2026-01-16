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
import org.toml.lang.psi.TomlArray;
import org.toml.lang.psi.TomlValue;
import org.toml.lang.psi.TomlVisitor;

import java.util.List;

public class TomlArrayImpl extends CompositePsiElement implements TomlArray {
    public TomlArrayImpl(IElementType type) {
        super(type);
    }

    @Nonnull
    @Override
    public List<TomlValue> getElements() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, TomlValue.class);
    }

    @Override
    public String toString() {
        return "TomlArray";
    }

    @Override
    public void accept(@Nonnull PsiElementVisitor visitor) {
        if (visitor instanceof TomlVisitor) {
            ((TomlVisitor) visitor).visitArray(this);
        } else {
            super.accept(visitor);
        }
    }
}
