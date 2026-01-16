/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.ide.resolve;

import consulo.annotation.component.ExtensionImpl;
import consulo.language.Language;
import consulo.language.impl.psi.path.WebReference;
import consulo.language.pattern.PlatformPatterns;
import consulo.language.psi.*;
import consulo.language.util.ProcessingContext;
import jakarta.annotation.Nonnull;
import org.toml.lang.TomlLanguage;
import org.toml.lang.psi.TomlLiteral;
import org.toml.lang.psi.ext.TomlLiteralExt;
import org.toml.lang.psi.ext.TomlLiteralKind;

@ExtensionImpl
public class TomlReferenceContributor extends PsiReferenceContributor {
    @Nonnull
    @Override
    public Language getLanguage() {
        return TomlLanguage.INSTANCE;
    }

    @Override
    public void registerReferenceProviders(@Nonnull PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(
                PlatformPatterns.psiElement(TomlLiteral.class),
                new TomlWebReferenceProvider(),
                PsiReferenceRegistrar.LOWER_PRIORITY
        );
    }

    private static class TomlWebReferenceProvider extends PsiReferenceProvider {
        @Override
        public boolean acceptsTarget(@Nonnull PsiElement target) {
            // web references do not point to any real PsiElement
            return false;
        }

        @Nonnull
        @Override
        public PsiReference[] getReferencesByElement(@Nonnull PsiElement element, @Nonnull ProcessingContext context) {
            if (!(element instanceof TomlLiteral)) {
                return PsiReference.EMPTY_ARRAY;
            }

            TomlLiteral literal = (TomlLiteral) element;
            TomlLiteralKind kind = TomlLiteralExt.getKind(literal);
            if (!(kind instanceof TomlLiteralKind.StringKind)) {
                return PsiReference.EMPTY_ARRAY;
            }

            if (!element.getText().contains(":")) {
                return PsiReference.EMPTY_ARRAY;
            }

            String textValue = ((TomlLiteralKind.StringKind) kind).getValue();
            if (textValue == null) {
                return PsiReference.EMPTY_ARRAY;
            }

            if (isWebReferenceUrl(textValue)) {
                return new PsiReference[]{new WebReference(element, textValue)};
            }

            return PsiReference.EMPTY_ARRAY;
        }

        private boolean isWebReferenceUrl(String url) {
            return url.startsWith("http://") || url.startsWith("https://") || url.startsWith("about:") || url.startsWith("mailto:");
        }
    }
}
