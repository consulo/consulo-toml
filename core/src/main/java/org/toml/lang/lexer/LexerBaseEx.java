/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.lang.lexer;

import consulo.language.ast.IElementType;
import consulo.language.lexer.LexerBase;
import jakarta.annotation.Nullable;

/**
 * Small utility class to ease implementing LexerBase.
 */
public abstract class LexerBaseEx extends LexerBase {
    private int state = 0;
    protected int tokenStart = 0;
    protected int tokenEnd = 0;
    protected CharSequence bufferSequence;
    protected int bufferEnd = 0;
    private IElementType tokenType = null;

    /**
     * Determine type of the current token (the one delimited by tokenStart and tokenEnd).
     */
    @Nullable
    protected abstract IElementType determineTokenType();

    /**
     * Find next token location (the one starting with tokenEnd and ending somewhere).
     *
     * @return end offset of the next token
     */
    protected abstract int locateToken(int start);

    @Override
    public void start(CharSequence buffer, int startOffset, int endOffset, int initialState) {
        bufferSequence = buffer;
        bufferEnd = endOffset;
        state = initialState;

        tokenEnd = startOffset;
        advance();
    }

    @Override
    public void advance() {
        tokenStart = tokenEnd;
        tokenEnd = locateToken(tokenStart);
        tokenType = determineTokenType();
    }

    @Nullable
    @Override
    public IElementType getTokenType() {
        return tokenType;
    }

    @Override
    public int getState() {
        return state;
    }

    @Override
    public int getTokenStart() {
        return tokenStart;
    }

    @Override
    public int getTokenEnd() {
        return tokenEnd;
    }

    @Override
    public CharSequence getBufferSequence() {
        return bufferSequence;
    }

    @Override
    public int getBufferEnd() {
        return bufferEnd;
    }
}
