package consulo.toml.ide;

import consulo.annotation.component.ExtensionImpl;
import org.jetbrains.annotations.NotNull;
import org.toml.lang.psi.TomlLiteral;
import org.toml.lang.psi.TomlStringLiteralManipulator;

@ExtensionImpl
public class JavaTomlStringLiteralManipulator  extends TomlStringLiteralManipulator {
    @NotNull
    @Override
    public Class<TomlLiteral> getElementClass() {
        return TomlLiteral.class;
    }
}
