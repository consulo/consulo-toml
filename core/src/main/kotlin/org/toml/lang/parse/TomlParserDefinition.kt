/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.lang.parse

import consulo.annotation.component.ExtensionImpl
import consulo.language.Language
import consulo.language.ast.ASTNode
import consulo.language.ast.IFileElementType
import consulo.language.ast.TokenSet
import consulo.language.file.FileViewProvider
import consulo.language.lexer.Lexer
import consulo.language.parser.ParserDefinition
import consulo.language.parser.PsiParser
import consulo.language.psi.PsiElement
import consulo.language.psi.PsiFile
import consulo.language.util.LanguageUtil
import consulo.language.version.LanguageVersion
import org.toml.lang.TomlLanguage
import org.toml.lang.lexer.TomlLexer
import org.toml.lang.psi.TOML_COMMENTS
import org.toml.lang.psi.TomlFile

@ExtensionImpl
class TomlParserDefinition : ParserDefinition {
    override fun createParser(version : LanguageVersion): PsiParser = TomlParser()

    override fun createFile(viewProvider: FileViewProvider): PsiFile = TomlFile(viewProvider)

    override fun spaceExistenceTypeBetweenTokens(left: ASTNode, right: ASTNode): ParserDefinition.SpaceRequirements =
        LanguageUtil.canStickTokensTogetherByLexer(left, right, TomlLexer())

    override fun getStringLiteralElements(version : LanguageVersion): TokenSet = TokenSet.EMPTY

    override fun getCommentTokens(version : LanguageVersion): TokenSet = TOML_COMMENTS

    override fun getFileNodeType(): IFileElementType = FILE

    override fun createLexer(version : LanguageVersion): Lexer = TomlLexer()

    override fun createElement(node: ASTNode): PsiElement =
        throw UnsupportedOperationException(node.elementType.toString()) // See org.toml.lang.psi.impl.TomlASTFactory

    override fun getLanguage(): Language = TomlLanguage;
    companion object {
        val FILE: IFileElementType = IFileElementType(TomlLanguage)
    }
}
