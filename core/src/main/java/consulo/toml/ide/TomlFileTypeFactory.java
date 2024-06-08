package consulo.toml.ide;

import consulo.annotation.component.ExtensionImpl;
import consulo.virtualFileSystem.fileType.FileNameMatcherFactory;
import consulo.virtualFileSystem.fileType.FileTypeConsumer;
import consulo.virtualFileSystem.fileType.FileTypeFactory;
import jakarta.inject.Inject;
import org.jetbrains.annotations.NotNull;
import org.toml.lang.psi.TomlFileType;

@ExtensionImpl
public class TomlFileTypeFactory extends FileTypeFactory {
    private final FileNameMatcherFactory myMatcherFactory;

    @Inject
    public TomlFileTypeFactory(FileNameMatcherFactory matcherFactory) {
        myMatcherFactory = matcherFactory;
    }

    @Override
    public void createFileTypes(@NotNull FileTypeConsumer fileTypeConsumer) {
        fileTypeConsumer.consume(TomlFileType.INSTANCE, "toml");

        for (String name : new String[]{"Cargo.lock", "Cargo.toml.orig", "Gopkg.lock", "Pipfile", "poetry.lock"}) {
            fileTypeConsumer.consume(TomlFileType.INSTANCE, myMatcherFactory.createExactFileNameMatcher(name));
        }
    }
}
