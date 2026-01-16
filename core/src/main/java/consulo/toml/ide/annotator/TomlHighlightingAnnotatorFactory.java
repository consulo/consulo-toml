/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package consulo.toml.ide.annotator;

import consulo.annotation.component.ExtensionImpl;
import consulo.application.dumb.DumbAware;
import consulo.language.Language;
import consulo.language.editor.annotation.Annotator;
import consulo.language.editor.annotation.AnnotatorFactory;
import jakarta.annotation.Nonnull;
import org.toml.ide.annotator.TomlHighlightingAnnotator;
import org.toml.lang.TomlLanguage;

@ExtensionImpl
public class TomlHighlightingAnnotatorFactory implements AnnotatorFactory, DumbAware {
    @Nonnull
    @Override
    public Language getLanguage() {
        return TomlLanguage.INSTANCE;
    }

    @Nonnull
    @Override
    public Annotator createAnnotator() {
        return new TomlHighlightingAnnotator();
    }
}
