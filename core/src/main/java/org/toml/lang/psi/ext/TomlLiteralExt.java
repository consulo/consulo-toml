/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.lang.psi.ext;

import consulo.language.ast.ASTNode;
import jakarta.annotation.Nullable;
import org.toml.lang.psi.TomlLiteral;
import org.toml.lang.psi.TomlTokenSets;

public final class TomlLiteralExt {
    private TomlLiteralExt() {
    }

    @Nullable
    public static TomlLiteralKind getKind(TomlLiteral literal) {
        ASTNode child = literal.getNode().findChildByType(TomlTokenSets.TOML_LITERALS);
        if (child == null) {
            return null;
        }
        TomlLiteralKind kind = TomlLiteralKind.fromAstNode(child);
        if (kind == null) {
            throw new IllegalStateException("Unknown literal: " + child + " (`" + literal.getText() + "`)");
        }
        return kind;
    }
}
