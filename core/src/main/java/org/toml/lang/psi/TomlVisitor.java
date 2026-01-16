/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.lang.psi;

import consulo.language.psi.PsiElementVisitor;

public class TomlVisitor extends PsiElementVisitor {
    public void visitElement(TomlElement element) {
        super.visitElement(element);
    }

    public void visitValue(TomlValue element) {
        visitElement(element);
    }

    public void visitKeyValue(TomlKeyValue element) {
        visitElement(element);
    }

    public void visitKeySegment(TomlKeySegment element) {
        visitElement(element);
    }

    public void visitKey(TomlKey element) {
        visitElement(element);
    }

    public void visitLiteral(TomlLiteral element) {
        visitValue(element);
    }

    public void visitKeyValueOwner(TomlKeyValueOwner element) {
        visitElement(element);
    }

    public void visitArray(TomlArray element) {
        visitValue(element);
    }

    public void visitTable(TomlTable element) {
        visitKeyValueOwner(element);
    }

    public void visitTableHeader(TomlTableHeader element) {
        visitElement(element);
    }

    public void visitInlineTable(TomlInlineTable element) {
        visitKeyValueOwner(element);
    }

    public void visitArrayTable(TomlArrayTable element) {
        visitKeyValueOwner(element);
    }
}
