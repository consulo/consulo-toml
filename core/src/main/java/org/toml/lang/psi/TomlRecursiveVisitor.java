/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.lang.psi;

import consulo.application.progress.ProgressManager;
import consulo.language.psi.PsiElement;
import consulo.language.psi.PsiRecursiveVisitor;

public class TomlRecursiveVisitor extends TomlVisitor implements PsiRecursiveVisitor {
    @Override
    public void visitElement(PsiElement element) {
        ProgressManager.checkCanceled();
        element.acceptChildren(this);
    }

    @Override
    public void visitElement(TomlElement element) {
        visitElement((PsiElement) element);
    }
}
