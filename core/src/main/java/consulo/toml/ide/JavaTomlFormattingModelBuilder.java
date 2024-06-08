package consulo.toml.ide;

import consulo.annotation.component.ExtensionImpl;
import consulo.language.Language;
import org.jetbrains.annotations.NotNull;
import org.toml.ide.formatter.TomlFormattingModelBuilder;
import org.toml.lang.TomlLanguage;

@ExtensionImpl
public class JavaTomlFormattingModelBuilder extends TomlFormattingModelBuilder {
    @NotNull
    @Override
    public Language getLanguage() {
        return TomlLanguage.INSTANCE;
    }
}
