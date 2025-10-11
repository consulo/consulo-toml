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
import consulo.localize.LocalizeValue
import consulo.project.Project
import consulo.toml.localize.TomlLocalize
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
                    HighlightSeverity.ERROR, TomlLocalize.inspectionTomlMessageInlineTablesOnSingleLine()).create()
            }
        }

        val parent = element.parent
        if (element.elementType == TomlElementTypes.COMMA && parent is TomlInlineTable &&
            element.textOffset > parent.entries.lastOrNull()?.textOffset ?: 0) {
            val message = TomlLocalize.intentionTomlNameRemoveTrailingComma()

            val fix = object : LocalQuickFix {
                override fun getName(): LocalizeValue = message
                override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
                    descriptor.psiElement?.delete()
                }
            }

            val problemDescriptor = InspectionManager.getInstance(element.project)
                .createProblemDescriptor(element, message.get(), fix, ProblemHighlightType.ERROR, true)

            holder.newAnnotation(HighlightSeverity.ERROR, TomlLocalize.inspectionTomlMessageTrailingCommasInInlineTables().get())
                .newLocalQuickFix(fix, problemDescriptor)
                .registerFix()
                .create()
        }
    }
}
