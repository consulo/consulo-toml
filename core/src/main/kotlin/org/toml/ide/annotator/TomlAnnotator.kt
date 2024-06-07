/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.ide.annotator

import consulo.language.editor.annotation.AnnotationHolder
import consulo.language.editor.annotation.HighlightSeverity
import consulo.language.editor.inspection.LocalQuickFix
import consulo.language.editor.inspection.ProblemDescriptor
import consulo.language.editor.inspection.ProblemHighlightType
import consulo.language.editor.inspection.scheme.InspectionManager
import consulo.language.psi.PsiElement
import consulo.language.psi.PsiWhiteSpace
import consulo.language.psi.SyntaxTraverser
import consulo.project.Project
import org.toml.TomlBundle
import org.toml.lang.psi.TomlArray
import org.toml.lang.psi.TomlElementTypes
import org.toml.lang.psi.TomlInlineTable
import org.toml.lang.psi.ext.elementType

class TomlAnnotator : AnnotatorBase() {
    override fun annotateInternal(element: PsiElement, holder: AnnotationHolder) {
        if (element is TomlInlineTable) {
            val whiteSpaces = SyntaxTraverser.psiTraverser(element)
                .expand { it !is TomlArray } // An array can be multiline even inside an inline table
                .filterIsInstance<PsiWhiteSpace>()
            if (whiteSpaces.any { it.textContains('\n') }) {
                holder.newAnnotation(
                    HighlightSeverity.ERROR,
                  TomlBundle.message("inspection.toml.message.inline.tables.on.single.line")).create()
            }
        }

        val parent = element.parent
        if (element.elementType == TomlElementTypes.COMMA && parent is TomlInlineTable &&
            element.textOffset > parent.entries.lastOrNull()?.textOffset ?: 0) {
            val message = TomlBundle.message("intention.toml.name.remove.trailing.comma")

            val fix = object : LocalQuickFix {
                override fun getFamilyName(): String = message
                override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
                    descriptor.psiElement?.delete()
                }
            }

            val problemDescriptor = InspectionManager.getInstance(element.project)
                .createProblemDescriptor(element, message, fix, ProblemHighlightType.ERROR, true)

            holder.newAnnotation(HighlightSeverity.ERROR, TomlBundle.message("inspection.toml.message.trailing.commas.in.inline.tables"))
                .newLocalQuickFix(fix, problemDescriptor)
                .registerFix()
                .create()
        }
    }
}
