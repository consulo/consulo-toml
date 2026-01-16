/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.ide.annotator;

import consulo.application.ApplicationManager;
import consulo.language.ast.IElementType;
import consulo.language.editor.annotation.AnnotationHolder;
import consulo.language.editor.annotation.HighlightSeverity;
import consulo.language.psi.PsiElement;
import jakarta.annotation.Nonnull;
import org.toml.ide.colors.TomlColor;
import org.toml.lang.psi.TomlElementTypes;

public class TomlHighlightingAnnotator extends AnnotatorBase {
    @Override
    protected void annotateInternal(@Nonnull PsiElement element, @Nonnull AnnotationHolder holder) {
        if (holder.isBatchMode()) {
            return;
        }

        // Although we use only elementType info, it's not possible to do it by lexer
        // because numbers and dates can be used as keys too (for example, `123 = 123` is correct toml)
        // and proper element types are set by parser.
        // see `org.toml.lang.parse.TomlParserUtil.remap`
        IElementType elementType = element.getNode().getElementType();
        TomlColor color;

        if (elementType == TomlElementTypes.NUMBER) {
            color = TomlColor.NUMBER;
        } else if (elementType == TomlElementTypes.DATE_TIME) {
            color = TomlColor.DATE;
        } else if (elementType == TomlElementTypes.BARE_KEY) {
            color = TomlColor.KEY;
        } else {
            return;
        }

        HighlightSeverity severity = ApplicationManager.getApplication().isUnitTestMode()
                ? color.getTestSeverity()
                : HighlightSeverity.INFORMATION;

        holder.newSilentAnnotation(severity)
                .textAttributes(color.getTextAttributesKey())
                .create();
    }
}
