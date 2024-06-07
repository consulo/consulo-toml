package consulo.toml.ide;

import consulo.annotation.component.ExtensionImpl;
import consulo.language.ast.IElementType;
import consulo.language.impl.ast.ASTCompositeFactory;
import consulo.language.impl.ast.CompositeElement;
import org.jetbrains.annotations.NotNull;
import org.toml.lang.psi.TomlElementTypes;
import org.toml.lang.psi.impl.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@ExtensionImpl
public class TomlASTFactory implements ASTCompositeFactory, TomlElementTypes {
    private Map<IElementType, Function<IElementType, CompositeElement>> map = new HashMap<>();

    public TomlASTFactory() {
        map.put(KEY_VALUE, TomlKeyValueImpl::new);
        map.put(KEY_SEGMENT, TomlKeySegmentImpl::new);
        map.put(KEY, TomlKeyImpl::new);
        map.put(LITERAL, TomlLiteralImpl::new);
        map.put(ARRAY, TomlArrayImpl::new);
        map.put(TABLE, TomlTableImpl::new);
        map.put(TABLE_HEADER, TomlTableHeaderImpl::new);
        map.put(INLINE_TABLE, TomlInlineTableImpl::new);
        map.put(ARRAY_TABLE, TomlArrayTableImpl::new);
    }

    @NotNull
    @Override
    public CompositeElement createComposite(@NotNull IElementType type) {
        Function<IElementType, CompositeElement> function = Objects.requireNonNull(map.get(type));
        return function.apply(type);
    }

    @Override
    public boolean test(IElementType type) {
        return map.containsKey(type);
    }
}
