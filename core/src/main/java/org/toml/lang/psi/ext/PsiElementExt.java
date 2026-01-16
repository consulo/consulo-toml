/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.lang.psi.ext;

import consulo.language.ast.IElementType;
import consulo.language.psi.PsiElement;
import consulo.language.psi.PsiUtilCore;
import jakarta.annotation.Nonnull;

public final class PsiElementExt {
    private PsiElementExt() {
    }

    @Nonnull
    public static IElementType getElementType(@Nonnull PsiElement element) {
        return PsiUtilCore.getElementType(element);
    }
}
