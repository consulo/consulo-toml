/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.ide.annotator

import consulo.application.ApplicationManager
import consulo.disposer.Disposable
import consulo.disposer.Disposer
import consulo.language.editor.annotation.AnnotationHolder
import consulo.language.editor.annotation.Annotator
import consulo.language.psi.PsiElement
import org.jetbrains.annotations.TestOnly
import java.util.concurrent.ConcurrentHashMap

abstract class AnnotatorBase : Annotator {

    final override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        if (!ApplicationManager.getApplication().isUnitTestMode || javaClass in enabledAnnotators) {
            annotateInternal(element, holder)
        }
    }

    protected abstract fun annotateInternal(element: PsiElement, holder: AnnotationHolder)

    companion object {
        private val enabledAnnotators: MutableSet<Class<out AnnotatorBase>> = ConcurrentHashMap.newKeySet()

        @TestOnly
        fun enableAnnotator(annotatorClass: Class<out AnnotatorBase>, parentDisposable: Disposable) {
            enabledAnnotators += annotatorClass
            Disposer.register(parentDisposable) { enabledAnnotators -= annotatorClass }
        }
    }
}
