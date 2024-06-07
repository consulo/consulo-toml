/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.ide.search

import consulo.language.cacheBuilder.WordsScanner
import consulo.language.findUsage.FindUsagesProvider
import consulo.language.psi.PsiElement

/**
 * This basic provider just enables `Find Usages` action for TOML language. Implement
 * [com.intellij.find.findUsages.FindUsagesHandlerFactory],
 * [com.intellij.psi.ElementDescriptionProvider] and
 * [com.intellij.usages.impl.rules.UsageTypeProviderEx]
 * in your plugin to make find usages for your TOML use-case.
 */
abstract class TomlFindUsagesProvider : FindUsagesProvider {
    /** If null, use default [com.intellij.lang.cacheBuilder.SimpleWordsScanner] here */
    override fun getWordsScanner(): WordsScanner? = null

    override fun canFindUsagesFor(element: PsiElement): Boolean = false

    override fun getType(element: PsiElement): String = ""
    override fun getDescriptiveName(element: PsiElement): String = ""
    override fun getNodeText(element: PsiElement, useFullName: Boolean): String = ""
}
