/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.ide.intentions

import consulo.codeEditor.Editor
import consulo.language.psi.PsiElement
import consulo.language.psi.util.PsiTreeUtil
import consulo.localize.LocalizeValue
import consulo.project.Project
import consulo.toml.localize.TomlLocalize
import org.toml.lang.psi.*
import java.util.function.Predicate

class TomlExpandInlineTableIntention : TomlElementBaseIntentionAction<TomlExpandInlineTableIntention.Context>() {
    override fun getText(): LocalizeValue = TomlLocalize.intentionTomlNameExpandIntoSeparateTable()
    //override fun getFamilyName(): String = text

    override fun findApplicableContext(project: Project, editor: Editor, element: PsiElement): Context? {
        var children = PsiTreeUtil.collectParents(element, TomlKeyValue::class.java, false, Predicate { false })

        val keyValue = children.firstOrNull {
            val hasInlineTableValue =  it.value is TomlInlineTable
            val hasTableOrArrayParent = it.parent is TomlKeyValueOwner && it.parent is TomlHeaderOwner
            val hasTomlFileParent = it.parent is TomlFile

            hasInlineTableValue && (hasTableOrArrayParent || hasTomlFileParent)
        } ?: return null

        val inlineTable = keyValue.value as TomlInlineTable
        val parentTable = keyValue.parent as? TomlKeyValueOwner
        return Context(keyValue, inlineTable, parentTable)
    }

    override fun invoke(project: Project, editor: Editor, ctx: Context) {
        val key = ctx.keyValue.key.text
        val parentTable = ctx.parentTable

        val newTableKey = if (parentTable != null) {
            val parentHeader = (parentTable as? TomlHeaderOwner)?.header ?: return
            val parentTableKey = parentHeader.key?.text ?: return
            "$parentTableKey.$key"
        } else {
            key
        }

        val newTable = TomlPsiFactory(project).createTable(newTableKey)
        val psiFactory = TomlPsiFactory(project)
        for (entry in ctx.inlineTable.entries) {
            newTable.add(psiFactory.createNewline())
            newTable.add(entry.copy())
        }

        val addedTableOffset = if (parentTable != null) {
            ctx.keyValue.delete()

            val parent = parentTable.parent
            val addedTable = parent.addAfter(newTable, parentTable)

            parent.addAfter(psiFactory.createWhitespace("\n\n"), parentTable)
            addedTable.textRange.endOffset
        } else {
            ctx.keyValue.replace(newTable).textRange.endOffset
        }

        editor.caretModel.moveToOffset(addedTableOffset)
    }

    class Context(val keyValue: TomlKeyValue, val inlineTable: TomlInlineTable, val parentTable: TomlKeyValueOwner?)
}
