/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.ide.folding

import consulo.annotation.component.ExtensionImpl
import consulo.application.dumb.DumbAware
import consulo.document.Document
import consulo.document.util.TextRange
import consulo.language.Language
import consulo.language.ast.ASTNode
import consulo.language.editor.folding.CustomFoldingBuilder
import consulo.language.editor.folding.FoldingDescriptor
import consulo.language.psi.PsiElement
import org.toml.lang.TomlLanguage
import org.toml.lang.psi.*

@ExtensionImpl
class TomlFoldingBuilder : CustomFoldingBuilder(), DumbAware {
    override fun getLanguage(): Language = TomlLanguage

    override fun buildLanguageFoldRegions(
        descriptors: MutableList<FoldingDescriptor>,
        root: PsiElement,
        document: Document,
        quick: Boolean
    ) {
        if (root !is TomlFile) return

        val visitor = TomlFoldingVisitor(descriptors)
        root.accept(visitor)
    }

    override fun getLanguagePlaceholderText(node: ASTNode, range: TextRange): String =
        when (node.elementType) {
            TomlElementTypes.ARRAY -> "[...]"
            else -> "{...}"
        }

    override fun isRegionCollapsedByDefault(node: ASTNode): Boolean = false
}

private class TomlFoldingVisitor(private val descriptors: MutableList<FoldingDescriptor>): TomlRecursiveVisitor() {
    override fun visitTable(element: TomlTable) {
        if (element.entries.isNotEmpty()) {
            foldChildren(element, element.header.nextSibling, element.lastChild)
            super.visitTable(element)
        }
    }

    override fun visitArrayTable(element: TomlArrayTable) {
        if (element.entries.isNotEmpty()) {
            foldChildren(element, element.header.nextSibling, element.lastChild)
            super.visitArrayTable(element)
        }
    }

    override fun visitInlineTable(element: TomlInlineTable) {
        if (element.entries.isNotEmpty()) {
            fold(element)
            super.visitInlineTable(element)
        }
    }

    override fun visitArray(element: TomlArray) {
        if (element.elements.isNotEmpty()) {
            fold(element)
            super.visitArray(element)
        }
    }

    private fun fold(element: PsiElement) {
        descriptors += FoldingDescriptor(element.node, element.textRange)
    }

    private fun foldChildren(element: PsiElement, firstChild: PsiElement, lastChild: PsiElement) {
        val start = firstChild.textRange.startOffset
        val end = lastChild.textRange.endOffset
        descriptors += FoldingDescriptor(element.node, TextRange(start, end))
    }
}
