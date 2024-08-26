/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.ide.search

import consulo.annotation.component.ExtensionImpl
import consulo.language.Language
import consulo.language.cacheBuilder.WordsScanner
import consulo.language.findUsage.FindUsagesProvider
import consulo.language.psi.PsiElement
import org.toml.lang.TomlLanguage

/**
 * This basic provider just enables `Find Usages` action for TOML language. Implement
 * [com.intellij.find.findUsages.FindUsagesHandlerFactory],
 * [com.intellij.psi.ElementDescriptionProvider] and
 * [com.intellij.usages.impl.rules.UsageTypeProviderEx]
 * in your plugin to make find usages for your TOML use-case.
 */
@ExtensionImpl
class TomlFindUsagesProvider : FindUsagesProvider {
    /** If null, use default [com.intellij.lang.cacheBuilder.SimpleWordsScanner] here */
    override fun getWordsScanner(): WordsScanner? = null

    override fun canFindUsagesFor(element: PsiElement): Boolean = false

    override fun getType(element: PsiElement): String = ""
    override fun getDescriptiveName(element: PsiElement): String = ""
    override fun getNodeText(element: PsiElement, useFullName: Boolean): String = ""
    override fun getLanguage(): Language = TomlLanguage;
}
