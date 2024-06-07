package consulo.toml.ide;

import consulo.annotation.component.ExtensionImpl;
import consulo.language.Language;
import org.jetbrains.annotations.NotNull;
import org.toml.ide.folding.TomlFoldingBuilder;
import org.toml.lang.TomlLanguage;

@ExtensionImpl
public class JavaTomlFoldingBuilder extends TomlFoldingBuilder {
    @NotNull
    @Override
    public Language getLanguage() {
        return TomlLanguage.INSTANCE;
    }
}
