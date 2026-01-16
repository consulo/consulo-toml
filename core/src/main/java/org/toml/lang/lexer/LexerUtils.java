/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.lang.lexer;

import consulo.language.ast.IElementType;
import consulo.language.lexer.Lexer;
import consulo.util.lang.Pair;
import jakarta.annotation.Nonnull;

import java.util.Iterator;
import java.util.NoSuchElementException;

public final class LexerUtils {
    private LexerUtils() {
    }

    @Nonnull
    public static Iterable<Pair<IElementType, String>> tokenize(@Nonnull CharSequence chars, @Nonnull Lexer lexer) {
        return () -> new Iterator<>() {
            private boolean started = false;

            @Override
            public boolean hasNext() {
                if (!started) {
                    lexer.start(chars);
                    started = true;
                }
                return lexer.getTokenType() != null;
            }

            @Override
            public Pair<IElementType, String> next() {
                if (!started) {
                    lexer.start(chars);
                    started = true;
                }
                if (lexer.getTokenType() == null) {
                    throw new NoSuchElementException();
                }
                Pair<IElementType, String> result = Pair.create(lexer.getTokenType(), lexer.getTokenText());
                lexer.advance();
                return result;
            }
        };
    }
}
