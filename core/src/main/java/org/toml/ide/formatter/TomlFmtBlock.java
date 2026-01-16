/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.ide.formatter;

import consulo.document.util.TextRange;
import consulo.language.ast.ASTNode;
import consulo.language.codeStyle.*;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.toml.ide.formatter.impl.FormatterUtils;
import org.toml.ide.formatter.impl.IndentHelper;
import org.toml.ide.formatter.impl.SpacingHelper;
import org.toml.lang.psi.TomlElementTypes;

import java.util.ArrayList;
import java.util.List;

public class TomlFmtBlock implements ASTBlock {
    private final ASTNode node;
    private final Alignment alignment;
    private final Indent indent;
    private final Wrap wrap;
    private final TomlFmtContext ctx;
    private List<Block> mySubBlocks = null;
    private Boolean myIsIncomplete = null;

    public TomlFmtBlock(@Nonnull ASTNode node, @Nullable Alignment alignment, @Nullable Indent indent, @Nullable Wrap wrap, @Nonnull TomlFmtContext ctx) {
        this.node = node;
        this.alignment = alignment;
        this.indent = indent;
        this.wrap = wrap;
        this.ctx = ctx;
    }

    @Nonnull
    @Override
    public ASTNode getNode() {
        return node;
    }

    @Nonnull
    @Override
    public TextRange getTextRange() {
        return node.getTextRange();
    }

    @Nullable
    @Override
    public Alignment getAlignment() {
        return alignment;
    }

    @Nullable
    @Override
    public Indent getIndent() {
        return indent;
    }

    @Nullable
    @Override
    public Wrap getWrap() {
        return wrap;
    }

    @Nonnull
    @Override
    public List<Block> getSubBlocks() {
        if (mySubBlocks == null) {
            mySubBlocks = buildChildren();
        }
        return mySubBlocks;
    }

    @Nonnull
    private List<Block> buildChildren() {
        List<Block> blocks = new ArrayList<>();
        ASTNode[] children = node.getChildren(null);
        for (ASTNode childNode : children) {
            if (!FormatterUtils.isWhitespaceOrEmpty(childNode)) {
                blocks.add(TomlFormattingModelBuilder.createBlock(
                        childNode,
                        null,
                        IndentHelper.computeIndent(node, childNode),
                        null,
                        ctx
                ));
            }
        }
        return blocks;
    }

    @Nullable
    @Override
    public Spacing getSpacing(@Nullable Block child1, @Nonnull Block child2) {
        return SpacingHelper.computeSpacing(this, child1, child2, ctx);
    }

    @Nonnull
    @Override
    public ChildAttributes getChildAttributes(int newChildIndex) {
        Indent childIndent;
        if (node.getElementType() == TomlElementTypes.ARRAY) {
            childIndent = Indent.getNormalIndent();
        } else {
            childIndent = Indent.getNoneIndent();
        }
        return new ChildAttributes(childIndent, null);
    }

    @Override
    public boolean isLeaf() {
        return node.getFirstChildNode() == null;
    }

    @Override
    public boolean isIncomplete() {
        if (myIsIncomplete == null) {
            myIsIncomplete = FormatterUtil.isIncomplete(node);
        }
        return myIsIncomplete;
    }

    @Override
    public String toString() {
        return node.getText() + " " + getTextRange();
    }
}
