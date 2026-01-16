/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.ide.annotator;

import consulo.language.editor.annotation.AnnotationHolder;
import consulo.language.editor.annotation.HighlightSeverity;
import consulo.language.editor.inspection.LocalQuickFix;
import consulo.language.editor.inspection.ProblemDescriptor;
import consulo.language.editor.inspection.ProblemHighlightType;
import consulo.language.editor.inspection.scheme.InspectionManager;
import consulo.language.psi.PsiElement;
import consulo.language.psi.PsiUtilCore;
import consulo.language.psi.PsiWhiteSpace;
import consulo.language.psi.SyntaxTraverser;
import consulo.localize.LocalizeValue;
import consulo.project.Project;
import consulo.toml.localize.TomlLocalize;
import jakarta.annotation.Nonnull;
import org.toml.lang.psi.TomlArray;
import org.toml.lang.psi.TomlElementTypes;
import org.toml.lang.psi.TomlInlineTable;
import org.toml.lang.psi.TomlKeyValue;

import java.util.List;

public class TomlAnnotator extends AnnotatorBase {
    @Override
    protected void annotateInternal(@Nonnull PsiElement element, @Nonnull AnnotationHolder holder) {
        if (element instanceof TomlInlineTable) {
            TomlInlineTable inlineTable = (TomlInlineTable) element;
            Iterable<PsiWhiteSpace> whiteSpaces = SyntaxTraverser.psiTraverser(element)
                    .expand(e -> !(e instanceof TomlArray)) // An array can be multiline even inside an inline table
                    .filter(PsiWhiteSpace.class);

            for (PsiWhiteSpace ws : whiteSpaces) {
                if (ws.textContains('\n')) {
                    holder.newAnnotation(HighlightSeverity.ERROR,
                            TomlLocalize.inspectionTomlMessageInlineTablesOnSingleLine().get())
                            .create();
                    break;
                }
            }
        }

        PsiElement parent = element.getParent();
        if (PsiUtilCore.getElementType(element) == TomlElementTypes.COMMA && parent instanceof TomlInlineTable) {
            TomlInlineTable inlineTable = (TomlInlineTable) parent;
            List<TomlKeyValue> entries = inlineTable.getEntries();
            int lastOffset = entries.isEmpty() ? 0 : entries.get(entries.size() - 1).getTextOffset();

            if (element.getTextOffset() > lastOffset) {
                LocalizeValue message = TomlLocalize.intentionTomlNameRemoveTrailingComma();

                LocalQuickFix fix = new LocalQuickFix() {
                    @Nonnull
                    @Override
                    public LocalizeValue getName() {
                        return message;
                    }

                    @Override
                    public void applyFix(@Nonnull Project project, @Nonnull ProblemDescriptor descriptor) {
                        PsiElement psiElement = descriptor.getPsiElement();
                        if (psiElement != null) {
                            psiElement.delete();
                        }
                    }
                };

                ProblemDescriptor problemDescriptor = InspectionManager.getInstance(element.getProject())
                        .createProblemDescriptor(element, message.get(), fix, ProblemHighlightType.ERROR, true);

                holder.newAnnotation(HighlightSeverity.ERROR,
                        TomlLocalize.inspectionTomlMessageTrailingCommasInInlineTables().get())
                        .newLocalQuickFix(fix, problemDescriptor)
                        .registerFix()
                        .create();
            }
        }
    }
}
