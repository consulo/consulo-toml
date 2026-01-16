/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package consulo.toml.ide;

import consulo.annotation.component.ExtensionImpl;
import consulo.language.ast.IElementType;
import consulo.language.impl.ast.ASTCompositeFactory;
import consulo.language.impl.ast.CompositeElement;
import jakarta.annotation.Nonnull;
import org.toml.lang.psi.TomlElementTypes;
import org.toml.lang.psi.impl.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@ExtensionImpl
public class TomlASTFactory implements ASTCompositeFactory {
    private final Map<IElementType, Function<IElementType, CompositeElement>> map = new HashMap<>();

    public TomlASTFactory() {
        map.put(TomlElementTypes.KEY_VALUE, TomlKeyValueImpl::new);
        map.put(TomlElementTypes.KEY_SEGMENT, TomlKeySegmentImpl::new);
        map.put(TomlElementTypes.KEY, TomlKeyImpl::new);
        map.put(TomlElementTypes.LITERAL, TomlLiteralImpl::new);
        map.put(TomlElementTypes.ARRAY, TomlArrayImpl::new);
        map.put(TomlElementTypes.TABLE, TomlTableImpl::new);
        map.put(TomlElementTypes.TABLE_HEADER, TomlTableHeaderImpl::new);
        map.put(TomlElementTypes.INLINE_TABLE, TomlInlineTableImpl::new);
        map.put(TomlElementTypes.ARRAY_TABLE, TomlArrayTableImpl::new);
    }

    @Nonnull
    @Override
    public CompositeElement createComposite(@Nonnull IElementType type) {
        Function<IElementType, CompositeElement> function = map.get(type);
        if (function == null) {
            throw new IllegalStateException("Unknown TOML element type: " + type);
        }
        return function.apply(type);
    }

    @Override
    public boolean test(@Nonnull IElementType type) {
        return map.containsKey(type);
    }
}
