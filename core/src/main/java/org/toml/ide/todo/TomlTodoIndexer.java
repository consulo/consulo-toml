/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.ide.todo;

import consulo.annotation.component.ExtensionImpl;
import consulo.language.lexer.Lexer;
import consulo.language.psi.search.UsageSearchContext;
import consulo.language.psi.stub.BaseFilterLexer;
import consulo.language.psi.stub.OccurrenceConsumer;
import consulo.language.psi.stub.todo.LexerBasedTodoIndexer;
import consulo.virtualFileSystem.fileType.FileType;
import jakarta.annotation.Nonnull;
import org.toml.lang.lexer.TomlLexer;
import org.toml.lang.psi.TomlFileType;
import org.toml.lang.psi.TomlTokenSets;

@ExtensionImpl
public class TomlTodoIndexer extends LexerBasedTodoIndexer {
    @Nonnull
    @Override
    public FileType getFileType() {
        return TomlFileType.INSTANCE;
    }

    @Nonnull
    @Override
    public Lexer createLexer(@Nonnull OccurrenceConsumer consumer) {
        return new BaseFilterLexer(new TomlLexer(), consumer) {
            @Override
            public void advance() {
                if (TomlTokenSets.TOML_COMMENTS.contains(myDelegate.getTokenType())) {
                    scanWordsInToken(UsageSearchContext.IN_COMMENTS, false, false);
                    advanceTodoItemCountsInToken();
                }
                myDelegate.advance();
            }
        };
    }
}
