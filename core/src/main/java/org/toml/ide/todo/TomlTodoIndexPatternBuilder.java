/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.ide.todo;

import consulo.annotation.component.ExtensionImpl;
import consulo.language.ast.IElementType;
import consulo.language.ast.TokenSet;
import consulo.language.lexer.Lexer;
import consulo.language.psi.PsiFile;
import consulo.language.psi.search.IndexPatternBuilder;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.toml.lang.lexer.TomlLexer;
import org.toml.lang.psi.TomlFile;
import org.toml.lang.psi.TomlTokenSets;

@ExtensionImpl
public class TomlTodoIndexPatternBuilder implements IndexPatternBuilder {
    @Nullable
    @Override
    public Lexer getIndexingLexer(@Nonnull PsiFile file) {
        if (file instanceof TomlFile) {
            return new TomlLexer();
        }
        return null;
    }

    @Nullable
    @Override
    public TokenSet getCommentTokenSet(@Nonnull PsiFile file) {
        if (file instanceof TomlFile) {
            return TomlTokenSets.TOML_COMMENTS;
        }
        return null;
    }

    @Override
    public int getCommentStartDelta(@Nullable IElementType tokenType) {
        if (tokenType != null && TomlTokenSets.TOML_COMMENTS.contains(tokenType)) {
            return 1;
        }
        return 0;
    }

    @Override
    public int getCommentEndDelta(@Nullable IElementType tokenType) {
        return 0;
    }
}
