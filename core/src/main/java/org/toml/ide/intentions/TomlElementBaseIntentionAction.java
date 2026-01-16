/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.ide.intentions;

import consulo.application.ApplicationManager;
import consulo.codeEditor.Editor;
import consulo.language.editor.intention.BaseElementAtCaretIntentionAction;
import consulo.language.psi.PsiElement;
import consulo.project.Project;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * A base class for implementing intentions: actions available via "light bulb" / `Alt+Enter`.
 * <p>
 * The cool thing about intentions is their UX: there is a huge number of intentions,
 * and they all can be invoked with a single `Alt + Enter` shortcut. This is possible
 * because at the position of the cursor only small number of intentions is applicable.
 * <p>
 * So, intentions consists of two functions: findApplicableContext functions determines
 * if the intention can be applied at the given position, it is used to populate "light bulb" list.
 * invoke is called when the user selects the intention from the list and must apply the changes.
 * <p>
 * The context collected by findApplicableContext is gathered into Ctx object and is passed to
 * invoke. In general, invoke should be infallible: if you need to check if some element is not
 * null, do this in findApplicableContext and pass the element via the context.
 * <p>
 * findApplicableContext is executed under a read action, and invoke under a write action.
 *
 * @param <Ctx> The context type
 */
public abstract class TomlElementBaseIntentionAction<Ctx> extends BaseElementAtCaretIntentionAction {

    /**
     * Return null if the intention is not applicable, otherwise collect and return
     * all the necessary info to actually apply the intention.
     */
    @Nullable
    protected abstract Ctx findApplicableContext(@Nonnull Project project, @Nonnull Editor editor, @Nonnull PsiElement element);

    protected abstract void invoke(@Nonnull Project project, @Nonnull Editor editor, @Nonnull Ctx ctx);

    @Override
    public final void invoke(@Nonnull Project project, @Nonnull Editor editor, @Nonnull PsiElement element) {
        Ctx ctx = findApplicableContext(project, editor, element);
        if (ctx == null) {
            return;
        }
        invoke(project, editor, ctx);
    }

    @Override
    public final boolean isAvailable(@Nonnull Project project, @Nonnull Editor editor, @Nonnull PsiElement element) {
        checkReadAccessAllowed();
        return findApplicableContext(project, editor, element) != null;
    }

    private static void checkReadAccessAllowed() {
        if (!ApplicationManager.getApplication().isReadAccessAllowed()) {
            throw new IllegalStateException("Needs read action");
        }
    }
}
