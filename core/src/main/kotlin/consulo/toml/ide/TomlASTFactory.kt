package consulo.toml.ide

import consulo.annotation.component.ExtensionImpl
import consulo.language.ast.IElementType
import consulo.language.impl.ast.ASTCompositeFactory
import consulo.language.impl.ast.CompositeElement
import jakarta.annotation.Nonnull
import org.toml.lang.psi.TomlElementTypes
import org.toml.lang.psi.impl.*
import java.util.*
import java.util.function.Function

@ExtensionImpl
class TomlASTFactory : ASTCompositeFactory, TomlElementTypes {
    private val map: MutableMap<IElementType, Function<IElementType, CompositeElement>> = HashMap()

    init {
        map[TomlElementTypes.KEY_VALUE] =
            Function<IElementType, CompositeElement> { type: IElementType -> TomlKeyValueImpl(type) }
        map[TomlElementTypes.KEY_SEGMENT] =
            Function<IElementType, CompositeElement> { type: IElementType -> TomlKeySegmentImpl(type) }
        map[TomlElementTypes.KEY] =
            Function<IElementType, CompositeElement> { type: IElementType -> TomlKeyImpl(type) }
        map[TomlElementTypes.LITERAL] =
            Function<IElementType, CompositeElement> { type: IElementType -> TomlLiteralImpl(type) }
        map[TomlElementTypes.ARRAY] =
            Function<IElementType, CompositeElement> { type: IElementType -> TomlArrayImpl(type) }
        map[TomlElementTypes.TABLE] =
            Function<IElementType, CompositeElement> { type: IElementType -> TomlTableImpl(type) }
        map[TomlElementTypes.TABLE_HEADER] =
            Function<IElementType, CompositeElement> { type: IElementType -> TomlTableHeaderImpl(type) }
        map[TomlElementTypes.INLINE_TABLE] =
            Function<IElementType, CompositeElement> { type: IElementType -> TomlInlineTableImpl(type) }
        map[TomlElementTypes.ARRAY_TABLE] =
            Function<IElementType, CompositeElement> { type: IElementType -> TomlArrayTableImpl(type) }
    }

    @Nonnull
    override fun createComposite(@Nonnull type: IElementType): CompositeElement {
        val function = map[type]!!
        return function.apply(type)
    }

    override fun test(type: IElementType): Boolean {
        return map.containsKey(type)
    }
}
