package consulo.toml.ide.annotator

import consulo.annotation.component.ExtensionImpl
import consulo.application.dumb.DumbAware
import consulo.language.Language
import consulo.language.editor.annotation.Annotator
import consulo.language.editor.annotation.AnnotatorFactory
import org.toml.ide.annotator.TomlHighlightingAnnotator
import org.toml.lang.TomlLanguage

@ExtensionImpl
class TomlHighlightingAnnotatorFactory : AnnotatorFactory, DumbAware {
    override fun getLanguage(): Language {
        return TomlLanguage
    }

    override fun createAnnotator(): Annotator {
        return TomlHighlightingAnnotator()
    }
}