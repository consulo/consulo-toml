/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.lang.psi;

import consulo.annotation.component.ExtensionImpl;
import consulo.document.util.TextRange;
import consulo.language.psi.AbstractElementManipulator;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.toml.lang.psi.ext.TomlLiteralExt;
import org.toml.lang.psi.ext.TomlLiteralKind;

@ExtensionImpl
public class TomlStringLiteralManipulator extends AbstractElementManipulator<TomlLiteral> {

    @Nullable
    @Override
    public TomlLiteral handleContentChange(@Nonnull TomlLiteral element, @Nonnull TextRange range, String newContent) {
        String oldText = element.getText();
        String newText = oldText.substring(0, range.getStartOffset()) + newContent + oldText.substring(range.getEndOffset());

        TomlLiteral newLiteral = new TomlPsiFactory(element.getProject()).createLiteral(newText);
        return (TomlLiteral) element.replace(newLiteral);
    }

    @Nonnull
    @Override
    public TextRange getRangeInElement(@Nonnull TomlLiteral element) {
        TomlLiteralKind kind = TomlLiteralExt.getKind(element);
        if (kind instanceof TomlLiteralKind.StringKind) {
            TextRange value = ((TomlLiteralKind.StringKind) kind).getOffsets().getValue();
            if (value != null) {
                return value;
            }
        }
        return super.getRangeInElement(element);
    }

    @Nonnull
    @Override
    public Class<TomlLiteral> getElementClass() {
        return TomlLiteral.class;
    }
}
