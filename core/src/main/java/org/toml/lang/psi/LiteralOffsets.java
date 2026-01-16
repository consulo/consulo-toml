/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.lang.psi;

import consulo.document.util.TextRange;
import jakarta.annotation.Nullable;

import java.util.Objects;

/**
 * Stores offsets of distinguishable parts of a literal.
 */
public final class LiteralOffsets {
    @Nullable
    private final TextRange prefix;
    @Nullable
    private final TextRange openDelim;
    @Nullable
    private final TextRange value;
    @Nullable
    private final TextRange closeDelim;
    @Nullable
    private final TextRange suffix;

    public LiteralOffsets(@Nullable TextRange prefix, @Nullable TextRange openDelim,
                          @Nullable TextRange value, @Nullable TextRange closeDelim,
                          @Nullable TextRange suffix) {
        this.prefix = prefix;
        this.openDelim = openDelim;
        this.value = value;
        this.closeDelim = closeDelim;
        this.suffix = suffix;
    }

    @Nullable
    public TextRange getPrefix() {
        return prefix;
    }

    @Nullable
    public TextRange getOpenDelim() {
        return openDelim;
    }

    @Nullable
    public TextRange getValue() {
        return value;
    }

    @Nullable
    public TextRange getCloseDelim() {
        return closeDelim;
    }

    @Nullable
    public TextRange getSuffix() {
        return suffix;
    }

    public static LiteralOffsets fromEndOffsets(int prefixEnd, int openDelimEnd, int valueEnd,
                                                 int closeDelimEnd, int suffixEnd) {
        TextRange prefix = makeRange(0, prefixEnd);
        TextRange openDelim = makeRange(prefixEnd, openDelimEnd);

        TextRange value = makeRange(openDelimEnd, valueEnd);
        // empty value is still a value provided we have open delimiter
        if (value == null && openDelim != null) {
            value = TextRange.create(openDelimEnd, openDelimEnd);
        }

        TextRange closeDelim = makeRange(valueEnd, closeDelimEnd);
        TextRange suffix = makeRange(closeDelimEnd, suffixEnd);

        return new LiteralOffsets(prefix, openDelim, value, closeDelim, suffix);
    }

    @Nullable
    private static TextRange makeRange(int start, int end) {
        if (end - start > 0) {
            return new TextRange(start, end);
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LiteralOffsets that = (LiteralOffsets) o;
        return Objects.equals(prefix, that.prefix) &&
               Objects.equals(openDelim, that.openDelim) &&
               Objects.equals(value, that.value) &&
               Objects.equals(closeDelim, that.closeDelim) &&
               Objects.equals(suffix, that.suffix);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prefix, openDelim, value, closeDelim, suffix);
    }
}
