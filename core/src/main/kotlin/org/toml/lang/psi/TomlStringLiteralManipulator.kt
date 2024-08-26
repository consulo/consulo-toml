/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.lang.psi

import consulo.annotation.component.ExtensionImpl
import consulo.document.util.TextRange
import consulo.language.psi.AbstractElementManipulator
import org.toml.lang.psi.ext.TomlLiteralKind
import org.toml.lang.psi.ext.kind

@ExtensionImpl
class TomlStringLiteralManipulator : AbstractElementManipulator<TomlLiteral>() {
    override fun handleContentChange(element: TomlLiteral, range: TextRange, newContent: String?): TomlLiteral {
        val oldText = element.text
        val newText = "${oldText.substring(0, range.startOffset)}$newContent${oldText.substring(range.endOffset)}"

        val newLiteral = TomlPsiFactory(element.project).createLiteral(newText)
        return element.replace(newLiteral) as TomlLiteral
    }

    override fun getRangeInElement(element: TomlLiteral): TextRange {
        return (element.kind as? TomlLiteralKind.String)?.offsets?.value ?: super.getRangeInElement(element)
    }

    override fun getElementClass(): Class<TomlLiteral> {
        return TomlLiteral::class.java
    }
}
