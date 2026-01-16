/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.lang.parse;

import consulo.annotation.component.ExtensionImpl;
import consulo.language.Language;
import consulo.language.ast.ASTNode;
import consulo.language.ast.IFileElementType;
import consulo.language.ast.TokenSet;
import consulo.language.file.FileViewProvider;
import consulo.language.lexer.Lexer;
import consulo.language.parser.ParserDefinition;
import consulo.language.parser.PsiParser;
import consulo.language.psi.PsiElement;
import consulo.language.psi.PsiFile;
import consulo.language.util.LanguageUtil;
import consulo.language.version.LanguageVersion;
import jakarta.annotation.Nonnull;
import org.toml.lang.TomlLanguage;
import org.toml.lang.lexer.TomlLexer;
import org.toml.lang.psi.TomlFile;
import org.toml.lang.psi.TomlTokenSets;

@ExtensionImpl
public class TomlParserDefinition implements ParserDefinition {
    public static final IFileElementType FILE = new IFileElementType(TomlLanguage.INSTANCE);

    @Nonnull
    @Override
    public PsiParser createParser(@Nonnull LanguageVersion version) {
        return new TomlParser();
    }

    @Nonnull
    @Override
    public PsiFile createFile(@Nonnull FileViewProvider viewProvider) {
        return new TomlFile(viewProvider);
    }

    @Nonnull
    @Override
    public SpaceRequirements spaceExistenceTypeBetweenTokens(ASTNode left, ASTNode right) {
        return LanguageUtil.canStickTokensTogetherByLexer(left, right, new TomlLexer());
    }

    @Nonnull
    @Override
    public TokenSet getStringLiteralElements(@Nonnull LanguageVersion version) {
        return TokenSet.EMPTY;
    }

    @Nonnull
    @Override
    public TokenSet getCommentTokens(@Nonnull LanguageVersion version) {
        return TomlTokenSets.TOML_COMMENTS;
    }

    @Nonnull
    @Override
    public IFileElementType getFileNodeType() {
        return FILE;
    }

    @Nonnull
    @Override
    public Lexer createLexer(@Nonnull LanguageVersion version) {
        return new TomlLexer();
    }

    @Nonnull
    @Override
    public PsiElement createElement(@Nonnull ASTNode node) {
        throw new UnsupportedOperationException(node.getElementType().toString()); // See consulo.toml.ide.TomlASTFactory
    }

    @Nonnull
    @Override
    public Language getLanguage() {
        return TomlLanguage.INSTANCE;
    }
}
