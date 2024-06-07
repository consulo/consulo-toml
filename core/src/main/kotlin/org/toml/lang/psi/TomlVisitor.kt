/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.lang.psi

import consulo.application.progress.ProgressManager
import consulo.language.psi.PsiElement
import consulo.language.psi.PsiElementVisitor
import consulo.language.psi.PsiRecursiveVisitor

open class TomlVisitor : PsiElementVisitor() {
    open fun visitElement(element: TomlElement) {
        super.visitElement(element)
    }

    open fun visitValue(element: TomlValue) {
        visitElement(element)
    }

    open fun visitKeyValue(element: TomlKeyValue) {
        visitElement(element)
    }

    open fun visitKeySegment(element: TomlKeySegment) {
        visitElement(element)
    }

    open fun visitKey(element: TomlKey) {
        visitElement(element)
    }

    open fun visitLiteral(element: TomlLiteral) {
        visitValue(element)
    }

    open fun visitKeyValueOwner(element: TomlKeyValueOwner) {
        visitElement(element)
    }

    open fun visitArray(element: TomlArray) {
        visitValue(element)
    }

    open fun visitTable(element: TomlTable) {
        visitKeyValueOwner(element)
    }

    open fun visitTableHeader(element: TomlTableHeader) {
        visitElement(element)
    }

    open fun visitInlineTable(element: TomlInlineTable) {
        visitKeyValueOwner(element)
    }

    open fun visitArrayTable(element: TomlArrayTable) {
        visitKeyValueOwner(element)
    }
}

open class TomlRecursiveVisitor : TomlVisitor(), PsiRecursiveVisitor {
    override fun visitElement(element: PsiElement) {
        ProgressManager.checkCanceled()
        element.acceptChildren(this)
    }

    override fun visitElement(element: TomlElement) {
        visitElement(element as PsiElement)
    }
}
