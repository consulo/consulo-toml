package consulo.toml.ide

import consulo.annotation.component.ExtensionImpl
import consulo.language.Language
import consulo.language.editor.highlight.SyntaxHighlighter
import consulo.language.editor.highlight.SyntaxHighlighterFactory
import consulo.project.Project
import consulo.virtualFileSystem.VirtualFile
import org.toml.ide.TomlHighlighter
import org.toml.lang.TomlLanguage

@ExtensionImpl
class TomlHighlighterFactory : SyntaxHighlighterFactory() {
    override fun getLanguage(): Language {
        return TomlLanguage
    }

    override fun getSyntaxHighlighter(p0: Project?, p1: VirtualFile?): SyntaxHighlighter {
        return TomlHighlighter();
    }
}