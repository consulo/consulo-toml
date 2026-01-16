/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.ide.folding;

import consulo.annotation.component.ExtensionImpl;
import consulo.application.dumb.DumbAware;
import consulo.document.Document;
import consulo.document.util.TextRange;
import consulo.language.Language;
import consulo.language.ast.ASTNode;
import consulo.language.editor.folding.CustomFoldingBuilder;
import consulo.language.editor.folding.FoldingDescriptor;
import consulo.language.psi.PsiElement;
import jakarta.annotation.Nonnull;
import org.toml.lang.TomlLanguage;
import org.toml.lang.psi.*;

import java.util.List;

@ExtensionImpl
public class TomlFoldingBuilder extends CustomFoldingBuilder implements DumbAware {
    @Nonnull
    @Override
    public Language getLanguage() {
        return TomlLanguage.INSTANCE;
    }

    @Override
    protected void buildLanguageFoldRegions(@Nonnull List<FoldingDescriptor> descriptors,
                                            @Nonnull PsiElement root,
                                            @Nonnull Document document,
                                            boolean quick) {
        if (!(root instanceof TomlFile)) {
            return;
        }

        TomlFoldingVisitor visitor = new TomlFoldingVisitor(descriptors);
        root.accept(visitor);
    }

    @Override
    protected String getLanguagePlaceholderText(@Nonnull ASTNode node, @Nonnull TextRange range) {
        if (node.getElementType() == TomlElementTypes.ARRAY) {
            return "[...]";
        }
        return "{...}";
    }

    @Override
    protected boolean isRegionCollapsedByDefault(@Nonnull ASTNode node) {
        return false;
    }

    private static class TomlFoldingVisitor extends TomlRecursiveVisitor {
        private final List<FoldingDescriptor> descriptors;

        TomlFoldingVisitor(List<FoldingDescriptor> descriptors) {
            this.descriptors = descriptors;
        }

        @Override
        public void visitTable(TomlTable element) {
            if (!element.getEntries().isEmpty()) {
                foldChildren(element, element.getHeader().getNextSibling(), element.getLastChild());
                super.visitTable(element);
            }
        }

        @Override
        public void visitArrayTable(TomlArrayTable element) {
            if (!element.getEntries().isEmpty()) {
                foldChildren(element, element.getHeader().getNextSibling(), element.getLastChild());
                super.visitArrayTable(element);
            }
        }

        @Override
        public void visitInlineTable(TomlInlineTable element) {
            if (!element.getEntries().isEmpty()) {
                fold(element);
                super.visitInlineTable(element);
            }
        }

        @Override
        public void visitArray(TomlArray element) {
            if (!element.getElements().isEmpty()) {
                fold(element);
                super.visitArray(element);
            }
        }

        private void fold(PsiElement element) {
            descriptors.add(new FoldingDescriptor(element.getNode(), element.getTextRange()));
        }

        private void foldChildren(PsiElement element, PsiElement firstChild, PsiElement lastChild) {
            int start = firstChild.getTextRange().getStartOffset();
            int end = lastChild.getTextRange().getEndOffset();
            descriptors.add(new FoldingDescriptor(element.getNode(), new TextRange(start, end)));
        }
    }
}
