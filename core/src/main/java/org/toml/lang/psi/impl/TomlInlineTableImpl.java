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
import org.toml.lang.psi.TomlInlineTable;
import org.toml.lang.psi.TomlKeyValue;
import org.toml.lang.psi.TomlVisitor;

import java.util.List;

public class TomlInlineTableImpl extends CompositePsiElement implements TomlInlineTable {
    public TomlInlineTableImpl(IElementType type) {
        super(type);
    }

    @Nonnull
    @Override
    public List<TomlKeyValue> getEntries() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, TomlKeyValue.class);
    }

    @Override
    public String toString() {
        return "TomlInlineTable";
    }

    @Override
    public void accept(@Nonnull PsiElementVisitor visitor) {
        if (visitor instanceof TomlVisitor) {
            ((TomlVisitor) visitor).visitInlineTable(this);
        } else {
            super.accept(visitor);
        }
    }
}
