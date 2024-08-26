/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.ide.todo

import consulo.annotation.component.ExtensionImpl
import consulo.language.lexer.Lexer
import consulo.language.psi.search.UsageSearchContext
import consulo.language.psi.stub.BaseFilterLexer
import consulo.language.psi.stub.OccurrenceConsumer
import consulo.language.psi.stub.todo.LexerBasedTodoIndexer
import consulo.virtualFileSystem.fileType.FileType
import org.toml.lang.lexer.TomlLexer
import org.toml.lang.psi.TOML_COMMENTS
import org.toml.lang.psi.TomlFileType

@ExtensionImpl
class TomlTodoIndexer : LexerBasedTodoIndexer() {
    override fun getFileType(): FileType {
        return TomlFileType
    }

    override fun createLexer(consumer: OccurrenceConsumer): Lexer = object : BaseFilterLexer(TomlLexer(), consumer) {
        override fun advance() {
            if (myDelegate.tokenType in TOML_COMMENTS) {
                scanWordsInToken(UsageSearchContext.IN_COMMENTS.toInt(), false, false)
                advanceTodoItemCountsInToken()
            }
            myDelegate.advance()
        }
    }
}
