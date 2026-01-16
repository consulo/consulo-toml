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
import org.toml.lang.psi.*;

import java.util.List;

public class TomlArrayTableImpl extends CompositePsiElement implements TomlArrayTable {
    public TomlArrayTableImpl(IElementType type) {
        super(type);
    }

    @Nonnull
    @Override
    public TomlTableHeader getHeader() {
        TomlTableHeader header = PsiTreeUtil.getChildOfType(this, TomlTableHeader.class);
        if (header == null) {
            throw new IllegalStateException("Invalid TOML PSI: Expected to find TomlTableHeader child of TomlArrayTable\nElement text:\n" + getText());
        }
        return header;
    }

    @Nonnull
    @Override
    public List<TomlKeyValue> getEntries() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, TomlKeyValue.class);
    }

    @Override
    public String toString() {
        return "TomlArrayTable";
    }

    @Override
    public void accept(@Nonnull PsiElementVisitor visitor) {
        if (visitor instanceof TomlVisitor) {
            ((TomlVisitor) visitor).visitArrayTable(this);
        } else {
            super.accept(visitor);
        }
    }
}
