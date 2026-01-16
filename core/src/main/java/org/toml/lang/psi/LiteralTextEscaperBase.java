/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.lang.psi;

import consulo.document.util.TextRange;
import consulo.language.psi.LiteralTextEscaper;
import consulo.language.psi.PsiLanguageInjectionHost;
import jakarta.annotation.Nonnull;

/**
 * See com.intellij.psi.impl.source.tree.injected.StringLiteralEscaper
 */
public abstract class LiteralTextEscaperBase<T extends PsiLanguageInjectionHost> extends LiteralTextEscaper<T> {
    private int[] outSourceOffsets;

    public LiteralTextEscaperBase(@Nonnull T host) {
        super(host);
    }

    @Override
    public boolean decode(@Nonnull TextRange rangeInsideHost, @Nonnull StringBuilder outChars) {
        String subText = rangeInsideHost.substring(myHost.getText());
        ParseResult result = parseStringCharacters(subText, outChars);
        outSourceOffsets = result.offsets;
        return result.success;
    }

    @Override
    public int getOffsetInHost(int offsetInDecoded, @Nonnull TextRange rangeInsideHost) {
        if (outSourceOffsets == null) {
            return -1;
        }
        int result = offsetInDecoded < outSourceOffsets.length ? outSourceOffsets[offsetInDecoded] : -1;
        if (result == -1) {
            return -1;
        }
        return (result <= rangeInsideHost.getLength() ? result : rangeInsideHost.getLength()) + rangeInsideHost.getStartOffset();
    }

    @Nonnull
    protected abstract ParseResult parseStringCharacters(@Nonnull String chars, @Nonnull StringBuilder outChars);

    public static class ParseResult {
        public final int[] offsets;
        public final boolean success;

        public ParseResult(@Nonnull int[] offsets, boolean success) {
            this.offsets = offsets;
            this.success = success;
        }
    }
}
