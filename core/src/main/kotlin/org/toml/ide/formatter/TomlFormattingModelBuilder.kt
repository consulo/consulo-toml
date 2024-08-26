/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.ide.formatter

import consulo.annotation.component.ExtensionImpl
import consulo.document.util.TextRange
import consulo.language.ast.ASTNode
import consulo.language.codeStyle.*
import consulo.language.psi.PsiFile
import org.toml.lang.TomlLanguage

@ExtensionImpl
class TomlFormattingModelBuilder : FormattingModelBuilder {
    override fun getRangeAffectingIndent(file: PsiFile?, offset: Int, elementAtOffset: ASTNode?): TextRange? = null

    override fun createModel(formattingContext: FormattingContext): FormattingModel {
        val settings = formattingContext.codeStyleSettings
        val element = formattingContext.psiElement
        val ctx = TomlFmtContext.create(settings)
        val block = createBlock(element.node, null, Indent.getNoneIndent(), null, ctx)
        return FormattingModelProvider.createFormattingModelForPsiFile(element.containingFile, block, settings)
    }

    override fun getLanguage() = TomlLanguage

    companion object {
        fun createBlock(
            node: ASTNode,
            alignment: Alignment?,
            indent: Indent?,
            wrap: Wrap?,
            ctx: TomlFmtContext
        ): ASTBlock = TomlFmtBlock(node, alignment, indent, wrap, ctx)
    }
}
