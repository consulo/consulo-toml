/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.lang.psi.ext;

import jakarta.annotation.Nullable;
import org.toml.lang.psi.TomlKey;
import org.toml.lang.psi.TomlKeySegment;

import java.util.List;

public final class TomlKeyExt {
    private TomlKeyExt() {
    }

    /**
     * If key consists of single TomlKeySegment, returns its name.
     * Otherwise, returns null
     */
    @Nullable
    public static String getName(TomlKey key) {
        List<TomlKeySegment> segments = key.getSegments();
        if (segments.size() == 1) {
            return segments.get(0).getName();
        }
        return null;
    }
}
