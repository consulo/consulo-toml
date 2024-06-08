package consulo.toml.ide;

import consulo.annotation.component.ExtensionImpl;
import consulo.language.Language;
import org.jetbrains.annotations.NotNull;
import org.toml.ide.resolve.TomlReferenceContributor;
import org.toml.lang.TomlLanguage;

@ExtensionImpl
public class JavaTomlReferenceContributor extends TomlReferenceContributor {
    @NotNull
    @Override
    public Language getLanguage() {
        return TomlLanguage.INSTANCE;
    }
}
