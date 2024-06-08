package consulo.toml.ide;

import consulo.annotation.component.ExtensionImpl;
import consulo.virtualFileSystem.fileType.FileType;
import org.jetbrains.annotations.NotNull;
import org.toml.ide.todo.TomlTodoIndexer;
import org.toml.lang.psi.TomlFileType;

@ExtensionImpl
public class JavaTomlTodoIndexer extends TomlTodoIndexer {
    @NotNull
    @Override
    public FileType getFileType() {
        return TomlFileType.INSTANCE;
    }
}
