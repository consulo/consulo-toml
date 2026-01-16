/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.ide.annotator;

import consulo.application.ApplicationManager;
import consulo.disposer.Disposable;
import consulo.disposer.Disposer;
import consulo.language.editor.annotation.AnnotationHolder;
import consulo.language.editor.annotation.Annotator;
import consulo.language.psi.PsiElement;
import jakarta.annotation.Nonnull;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AnnotatorBase implements Annotator {
    private static final Set<Class<? extends AnnotatorBase>> enabledAnnotators = ConcurrentHashMap.newKeySet();

    @Override
    public final void annotate(@Nonnull PsiElement element, @Nonnull AnnotationHolder holder) {
        if (!ApplicationManager.getApplication().isUnitTestMode() || enabledAnnotators.contains(getClass())) {
            annotateInternal(element, holder);
        }
    }

    protected abstract void annotateInternal(@Nonnull PsiElement element, @Nonnull AnnotationHolder holder);

    public static void enableAnnotator(@Nonnull Class<? extends AnnotatorBase> annotatorClass, @Nonnull Disposable parentDisposable) {
        enabledAnnotators.add(annotatorClass);
        Disposer.register(parentDisposable, () -> enabledAnnotators.remove(annotatorClass));
    }
}
