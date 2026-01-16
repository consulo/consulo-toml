/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.lang.psi.impl;

import consulo.language.ast.IElementType;
import consulo.language.impl.ast.LeafElement;
import consulo.language.impl.psi.CompositePsiElement;
import consulo.language.psi.LiteralTextEscaper;
import consulo.language.psi.PsiElementVisitor;
import consulo.language.psi.PsiLanguageInjectionHost;
import consulo.language.psi.PsiReference;
import consulo.language.psi.ReferenceProvidersRegistry;
import jakarta.annotation.Nonnull;
import org.toml.lang.psi.*;

public class TomlLiteralImpl extends CompositePsiElement implements TomlLiteral {
    public TomlLiteralImpl(IElementType type) {
        super(type);
    }

    @Nonnull
    @Override
    public PsiReference[] getReferences() {
        return ReferenceProvidersRegistry.getReferencesFromProviders(this);
    }

    @Override
    public String toString() {
        return "TomlLiteral";
    }

    @Override
    public boolean isValidHost() {
        return getNode().findChildByType(TomlTokenSets.TOML_STRING_LITERALS) != null;
    }

    @Override
    public PsiLanguageInjectionHost updateText(@Nonnull String text) {
        var valueNode = getNode().getFirstChildNode();
        assert valueNode instanceof LeafElement;
        ((LeafElement) valueNode).replaceWithText(text);
        return this;
    }

    @Nonnull
    @Override
    public LiteralTextEscaper<? extends PsiLanguageInjectionHost> createLiteralTextEscaper() {
        var child = getNode().findChildByType(TomlTokenSets.TOML_STRING_LITERALS);
        if (child == null) {
            throw new IllegalStateException(getText() + " is not string literal");
        }
        IElementType tokenType = child.getElementType();
        if (TomlTokenSets.TOML_BASIC_STRINGS.contains(tokenType)) {
            return new TomlLiteralTextEscaper(this);
        }
        return LiteralTextEscaper.createSimple(this);
    }

    @Override
    public void accept(@Nonnull PsiElementVisitor visitor) {
        if (visitor instanceof TomlVisitor) {
            ((TomlVisitor) visitor).visitLiteral(this);
        } else {
            super.accept(visitor);
        }
    }
}
