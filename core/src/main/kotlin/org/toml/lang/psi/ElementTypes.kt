/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.lang.psi

import consulo.language.ast.IElementType
import consulo.language.ast.TokenSet
import consulo.language.file.LanguageFileType
import consulo.localize.LocalizeValue
import consulo.toml.icon.TomlIconGroup
import consulo.toml.localize.TomlLocalize
import consulo.ui.image.Image
import consulo.virtualFileSystem.VirtualFile
import consulo.virtualFileSystem.fileType.FileTypeIdentifiableByVirtualFile
import org.toml.lang.TomlLanguage
import org.toml.lang.psi.TomlElementTypes.*

class TomlTokenType(debugName: String) : IElementType(debugName, TomlLanguage)
class TomlCompositeType(debugName: String) : IElementType(debugName, TomlLanguage)

object TomlFileType : LanguageFileType(TomlLanguage), FileTypeIdentifiableByVirtualFile {
    override fun getId(): String = "TOML"
    override fun getDescription(): LocalizeValue = TomlLocalize.filetypeTomlDescription()
    override fun getDefaultExtension(): String = "toml"
    override fun getIcon(): Image = TomlIconGroup.toml_file();
    override fun getCharset(file: VirtualFile, content: ByteArray): String = "UTF-8"

    override fun isMyFileType(file: VirtualFile): Boolean {
        return file.name == "config" && file.parent?.name == ".cargo"
    }
}

val TOML_COMMENTS = TokenSet.create(COMMENT)

val TOML_BASIC_STRINGS = TokenSet.create(BASIC_STRING, MULTILINE_BASIC_STRING)
val TOML_LITERAL_STRINGS = TokenSet.create(LITERAL_STRING, MULTILINE_LITERAL_STRING)
val TOML_SINGLE_LINE_STRINGS = TokenSet.create(BASIC_STRING, LITERAL_STRING)
val TOML_MULTILINE_STRINGS = TokenSet.create(MULTILINE_BASIC_STRING, MULTILINE_LITERAL_STRING)
val TOML_STRING_LITERALS = TokenSet.orSet(TOML_BASIC_STRINGS, TOML_LITERAL_STRINGS)
val TOML_LITERALS = TokenSet.orSet(
    TOML_STRING_LITERALS,
    TokenSet.create(
        BOOLEAN,
        NUMBER,
        DATE_TIME
    )
)
