/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.lang.psi.impl;

import consulo.language.ast.ASTNode;
import consulo.language.ast.IElementType;
import consulo.language.impl.psi.CompositePsiElement;
import consulo.language.psi.PsiElement;
import consulo.language.psi.PsiElementVisitor;
import consulo.language.psi.PsiReference;
import consulo.language.psi.ReferenceProvidersRegistry;
import consulo.navigation.ItemPresentation;
import consulo.ui.ex.tree.PresentationData;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.toml.lang.psi.*;
import org.toml.lang.psi.ext.TomlLiteralKind;

public class TomlKeySegmentImpl extends CompositePsiElement implements TomlKeySegment {
    public TomlKeySegmentImpl(IElementType type) {
        super(type);
    }

    @Nullable
    @Override
    public String getName() {
        ASTNode child = getNode().findChildByType(TomlTokenSets.TOML_SINGLE_LINE_STRINGS);
        if (child == null) {
            return getText();
        }
        TomlLiteralKind kind = TomlLiteralKind.fromAstNode(child);
        if (kind instanceof TomlLiteralKind.StringKind) {
            String value = ((TomlLiteralKind.StringKind) kind).getValue();
            return value != null ? value : getText();
        }
        return getText();
    }

    @Override
    public PsiElement setName(@Nonnull String name) {
        return replace(new TomlPsiFactory(getProject()).createKeySegment(name));
    }

    @Nullable
    @Override
    public ItemPresentation getPresentation() {
        return new PresentationData(getName(), null, null, null);
    }

    @Override
    public String toString() {
        return "TomlKeySegment";
    }

    @Nonnull
    @Override
    public PsiReference[] getReferences() {
        return ReferenceProvidersRegistry.getReferencesFromProviders(this);
    }

    @Override
    public void accept(@Nonnull PsiElementVisitor visitor) {
        if (visitor instanceof TomlVisitor) {
            ((TomlVisitor) visitor).visitKeySegment(this);
        } else {
            super.accept(visitor);
        }
    }
}
