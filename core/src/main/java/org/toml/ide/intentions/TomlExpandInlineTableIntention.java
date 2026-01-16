/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.ide.intentions;

import consulo.codeEditor.Editor;
import consulo.language.psi.PsiElement;
import consulo.language.psi.util.PsiTreeUtil;
import consulo.localize.LocalizeValue;
import consulo.project.Project;
import consulo.toml.localize.TomlLocalize;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.toml.lang.psi.*;

import java.util.Collection;
import java.util.List;

public class TomlExpandInlineTableIntention extends TomlElementBaseIntentionAction<TomlExpandInlineTableIntention.Context> {

    @Nonnull
    @Override
    public LocalizeValue getText() {
        return TomlLocalize.intentionTomlNameExpandIntoSeparateTable();
    }

    @Nullable
    @Override
    protected Context findApplicableContext(@Nonnull Project project, @Nonnull Editor editor, @Nonnull PsiElement element) {
        Collection<TomlKeyValue> parents = PsiTreeUtil.collectParents(element, TomlKeyValue.class, false, e -> false);

        for (TomlKeyValue keyValue : parents) {
            boolean hasInlineTableValue = keyValue.getValue() instanceof TomlInlineTable;
            boolean hasTableOrArrayParent = keyValue.getParent() instanceof TomlKeyValueOwner && keyValue.getParent() instanceof TomlHeaderOwner;
            boolean hasTomlFileParent = keyValue.getParent() instanceof TomlFile;

            if (hasInlineTableValue && (hasTableOrArrayParent || hasTomlFileParent)) {
                TomlInlineTable inlineTable = (TomlInlineTable) keyValue.getValue();
                TomlKeyValueOwner parentTable = keyValue.getParent() instanceof TomlKeyValueOwner ? (TomlKeyValueOwner) keyValue.getParent() : null;
                return new Context(keyValue, inlineTable, parentTable);
            }
        }

        return null;
    }

    @Override
    protected void invoke(@Nonnull Project project, @Nonnull Editor editor, @Nonnull Context ctx) {
        String key = ctx.keyValue.getKey().getText();
        TomlKeyValueOwner parentTable = ctx.parentTable;

        String newTableKey;
        if (parentTable != null) {
            if (!(parentTable instanceof TomlHeaderOwner)) {
                return;
            }
            TomlTableHeader parentHeader = ((TomlHeaderOwner) parentTable).getHeader();
            TomlKey parentKey = parentHeader.getKey();
            if (parentKey == null) {
                return;
            }
            String parentTableKey = parentKey.getText();
            newTableKey = parentTableKey + "." + key;
        } else {
            newTableKey = key;
        }

        TomlPsiFactory psiFactory = new TomlPsiFactory(project);
        TomlTable newTable = psiFactory.createTable(newTableKey);

        List<TomlKeyValue> entries = ctx.inlineTable.getEntries();
        for (TomlKeyValue entry : entries) {
            newTable.add(psiFactory.createNewline());
            newTable.add(entry.copy());
        }

        int addedTableOffset;
        if (parentTable != null) {
            ctx.keyValue.delete();

            PsiElement parent = ((PsiElement) parentTable).getParent();
            PsiElement addedTable = parent.addAfter(newTable, (PsiElement) parentTable);

            parent.addAfter(psiFactory.createWhitespace("\n\n"), (PsiElement) parentTable);
            addedTableOffset = addedTable.getTextRange().getEndOffset();
        } else {
            addedTableOffset = ctx.keyValue.replace(newTable).getTextRange().getEndOffset();
        }

        editor.getCaretModel().moveToOffset(addedTableOffset);
    }

    public static class Context {
        public final TomlKeyValue keyValue;
        public final TomlInlineTable inlineTable;
        @Nullable
        public final TomlKeyValueOwner parentTable;

        public Context(@Nonnull TomlKeyValue keyValue, @Nonnull TomlInlineTable inlineTable, @Nullable TomlKeyValueOwner parentTable) {
            this.keyValue = keyValue;
            this.inlineTable = inlineTable;
            this.parentTable = parentTable;
        }
    }
}
