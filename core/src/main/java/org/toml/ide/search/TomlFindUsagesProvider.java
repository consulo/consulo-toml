/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.ide.search;

import consulo.annotation.component.ExtensionImpl;
import consulo.language.Language;
import consulo.language.cacheBuilder.WordsScanner;
import consulo.language.findUsage.FindUsagesProvider;
import consulo.language.psi.PsiElement;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.toml.lang.TomlLanguage;

/**
 * This basic provider just enables `Find Usages` action for TOML language. Implement
 * FindUsagesHandlerFactory, ElementDescriptionProvider and UsageTypeProviderEx
 * in your plugin to make find usages for your TOML use-case.
 */
@ExtensionImpl
public class TomlFindUsagesProvider implements FindUsagesProvider {
    /**
     * If null, use default SimpleWordsScanner here
     */
    @Nullable
    @Override
    public WordsScanner getWordsScanner() {
        return null;
    }

    @Override
    public boolean canFindUsagesFor(@Nonnull PsiElement element) {
        return false;
    }

    @Nonnull
    @Override
    public String getType(@Nonnull PsiElement element) {
        return "";
    }

    @Nonnull
    @Override
    public String getDescriptiveName(@Nonnull PsiElement element) {
        return "";
    }

    @Nonnull
    @Override
    public String getNodeText(@Nonnull PsiElement element, boolean useFullName) {
        return "";
    }

    @Nonnull
    @Override
    public Language getLanguage() {
        return TomlLanguage.INSTANCE;
    }
}
