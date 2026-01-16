/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.lang.psi;

import java.util.List;

public interface TomlArray extends TomlValue {
    List<TomlValue> getElements();
}
