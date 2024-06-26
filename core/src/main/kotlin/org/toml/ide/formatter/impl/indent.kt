/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.ide.formatter.impl

import consulo.language.ast.ASTNode
import consulo.language.codeStyle.Indent
import org.toml.ide.formatter.TomlFmtBlock
import org.toml.lang.psi.TomlElementTypes.ARRAY

fun TomlFmtBlock.computeIndent(child: ASTNode): Indent? = when (node.elementType) {
    ARRAY -> getArrayIndent(child)
    else -> Indent.getNoneIndent()
}

private fun getArrayIndent(node: ASTNode): Indent =
    when {
        node.isArrayDelimiter() -> Indent.getNoneIndent()
        else -> Indent.getNormalIndent()
    }
