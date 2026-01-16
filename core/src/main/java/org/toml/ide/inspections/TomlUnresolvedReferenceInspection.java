/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.ide.inspections;

import consulo.language.editor.inspection.LocalInspectionTool;
import consulo.language.editor.inspection.ProblemHighlightType;
import consulo.language.editor.inspection.ProblemsHolder;
import consulo.language.psi.PsiElement;
import consulo.language.psi.PsiElementVisitor;
import consulo.language.psi.PsiReference;
import jakarta.annotation.Nonnull;
import org.toml.lang.psi.TomlLiteral;
import org.toml.lang.psi.TomlVisitor;
import org.toml.lang.psi.ext.TomlLiteralExt;
import org.toml.lang.psi.ext.TomlLiteralKind;

public abstract class TomlUnresolvedReferenceInspection extends LocalInspectionTool {
    @Nonnull
    @Override
    public PsiElementVisitor buildVisitor(@Nonnull ProblemsHolder holder, boolean isOnTheFly) {
        return new TomlVisitor() {
            @Override
            public void visitLiteral(TomlLiteral element) {
                TomlLiteralKind kind = TomlLiteralExt.getKind(element);
                if (!(kind instanceof TomlLiteralKind.StringKind)) {
                    return;
                }
                checkReference(element, holder);
            }
        };
    }

    private void checkReference(PsiElement element, ProblemsHolder holder) {
        for (PsiReference reference : element.getReferences()) {
            if (reference.resolve() == null) {
                holder.registerProblem(reference,
                        ProblemsHolder.unresolvedReferenceMessage(reference).get(),
                        ProblemHighlightType.LIKE_UNKNOWN_SYMBOL);
            }
        }
    }
}
