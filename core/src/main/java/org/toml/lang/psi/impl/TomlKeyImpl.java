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
import org.toml.lang.psi.TomlKey;
import org.toml.lang.psi.TomlKeySegment;
import org.toml.lang.psi.TomlVisitor;

import java.util.List;

public class TomlKeyImpl extends CompositePsiElement implements TomlKey {
    public TomlKeyImpl(IElementType type) {
        super(type);
    }

    @Nonnull
    @Override
    public List<TomlKeySegment> getSegments() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, TomlKeySegment.class);
    }

    @Override
    public String toString() {
        return "TomlKey";
    }

    @Override
    public void accept(@Nonnull PsiElementVisitor visitor) {
        if (visitor instanceof TomlVisitor) {
            ((TomlVisitor) visitor).visitKey(this);
        } else {
            super.accept(visitor);
        }
    }
}
