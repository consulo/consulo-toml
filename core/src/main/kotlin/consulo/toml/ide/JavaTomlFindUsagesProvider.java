package consulo.toml.ide;

import consulo.annotation.component.ExtensionImpl;
import consulo.language.Language;
import org.jetbrains.annotations.NotNull;
import org.toml.ide.search.TomlFindUsagesProvider;
import org.toml.lang.TomlLanguage;

@ExtensionImpl
public class JavaTomlFindUsagesProvider extends TomlFindUsagesProvider {
    @NotNull
    @Override
    public Language getLanguage() {
        return TomlLanguage.INSTANCE;
    }
}
