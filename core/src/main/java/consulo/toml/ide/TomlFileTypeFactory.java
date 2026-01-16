/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package consulo.toml.ide;

import consulo.annotation.component.ExtensionImpl;
import consulo.virtualFileSystem.fileType.FileNameMatcherFactory;
import consulo.virtualFileSystem.fileType.FileTypeConsumer;
import consulo.virtualFileSystem.fileType.FileTypeFactory;
import jakarta.annotation.Nonnull;
import jakarta.inject.Inject;
import org.toml.lang.psi.TomlFileType;

@ExtensionImpl
public class TomlFileTypeFactory extends FileTypeFactory {
    private final FileNameMatcherFactory matcherFactory;

    @Inject
    public TomlFileTypeFactory(@Nonnull FileNameMatcherFactory matcherFactory) {
        this.matcherFactory = matcherFactory;
    }

    @Override
    public void createFileTypes(@Nonnull FileTypeConsumer fileTypeConsumer) {
        fileTypeConsumer.consume(TomlFileType.INSTANCE, "toml");

        String[] names = {"Cargo.lock", "Cargo.toml.orig", "Gopkg.lock", "Pipfile", "poetry.lock"};
        for (String name : names) {
            fileTypeConsumer.consume(TomlFileType.INSTANCE, matcherFactory.createExactFileNameMatcher(name));
        }
    }
}
