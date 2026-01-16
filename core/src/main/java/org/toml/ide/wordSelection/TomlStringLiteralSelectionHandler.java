/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.ide.wordSelection;

import consulo.annotation.component.ExtensionImpl;
import consulo.codeEditor.Editor;
import consulo.document.util.TextRange;
import consulo.language.ast.IElementType;
import consulo.language.editor.action.ExtendWordSelectionHandlerBase;
import consulo.language.editor.action.SelectWordUtil;
import consulo.language.psi.PsiElement;
import consulo.language.psi.PsiUtilCore;
import jakarta.annotation.Nonnull;
import org.toml.lang.lexer.TomlEscapeLexer;
import org.toml.lang.psi.TomlTokenSets;
import org.toml.lang.psi.ext.TomlLiteralKind;

import java.util.ArrayList;
import java.util.List;

@ExtensionImpl
public class TomlStringLiteralSelectionHandler extends ExtendWordSelectionHandlerBase {
    @Override
    public boolean canSelect(@Nonnull PsiElement e) {
        return TomlTokenSets.TOML_STRING_LITERALS.contains(PsiUtilCore.getElementType(e));
    }

    @Override
    public List<TextRange> select(@Nonnull PsiElement e, @Nonnull CharSequence editorText, int cursorOffset, @Nonnull Editor editor) {
        TomlLiteralKind kind = TomlLiteralKind.fromAstNode(e.getNode());
        if (!(kind instanceof TomlLiteralKind.StringKind)) {
            return null;
        }

        TomlLiteralKind.StringKind stringKind = (TomlLiteralKind.StringKind) kind;
        TextRange valueRange = stringKind.getOffsets().getValue();
        if (valueRange == null) {
            return null;
        }

        valueRange = valueRange.shiftRight(kind.getNode().getStartOffset());

        List<TextRange> result = super.select(e, editorText, cursorOffset, editor);
        if (result == null) {
            result = new ArrayList<>();
        }

        IElementType elementType = PsiUtilCore.getElementType(e);
        if (TomlEscapeLexer.ESCAPABLE_LITERALS_TOKEN_SET.contains(elementType)) {
            SelectWordUtil.addWordHonoringEscapeSequences(
                    editorText,
                    valueRange,
                    cursorOffset,
                    TomlEscapeLexer.of(elementType),
                    result
            );
        }

        result.add(valueRange);
        return result;
    }
}
