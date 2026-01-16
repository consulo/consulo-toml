/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.ide;

import consulo.annotation.component.ExtensionImpl;
import consulo.language.BracePair;
import consulo.language.Language;
import consulo.language.PairedBraceMatcher;
import consulo.language.ast.IElementType;
import consulo.language.psi.PsiFile;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.toml.lang.TomlLanguage;
import org.toml.lang.psi.TomlElementTypes;

@ExtensionImpl
public class TomlBraceMatcher implements PairedBraceMatcher {
    private static final BracePair[] PAIRS = new BracePair[]{
            // Grammar Kit hack - ignore braces in recovery
            new BracePair(new IElementType("FAKE_L_BRACE", TomlLanguage.INSTANCE), new IElementType("FAKE_L_BRACE", TomlLanguage.INSTANCE), false),
            new BracePair(TomlElementTypes.L_CURLY, TomlElementTypes.R_CURLY, false),
            new BracePair(TomlElementTypes.L_BRACKET, TomlElementTypes.R_BRACKET, false)
    };

    @Nonnull
    @Override
    public Language getLanguage() {
        return TomlLanguage.INSTANCE;
    }

    @Nonnull
    @Override
    public BracePair[] getPairs() {
        return PAIRS;
    }

    @Override
    public boolean isPairedBracesAllowedBeforeType(@Nonnull IElementType lbraceType, @Nullable IElementType contextType) {
        return true;
    }

    @Override
    public int getCodeConstructStart(@Nonnull PsiFile file, int openingBraceOffset) {
        return openingBraceOffset;
    }
}
