package consulo.toml.ide;

import consulo.annotation.component.ExtensionImpl;
import consulo.language.Language;
import org.jetbrains.annotations.NotNull;
import org.toml.lang.TomlLanguage;
import org.toml.lang.parse.TomlParserDefinition;

@ExtensionImpl
public class JavaTomlParserDefinition extends TomlParserDefinition {
    @NotNull
    @Override
    public Language getLanguage() {
        return TomlLanguage.INSTANCE;
    }
}
