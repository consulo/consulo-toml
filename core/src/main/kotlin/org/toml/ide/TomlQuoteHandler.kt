/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.ide

import consulo.language.editor.action.SimpleTokenSetQuoteHandler
import org.toml.lang.psi.TomlElementTypes

class TomlQuoteHandler : SimpleTokenSetQuoteHandler(TomlElementTypes.BASIC_STRING, TomlElementTypes.LITERAL_STRING)
