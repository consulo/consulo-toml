package consulo.toml.ide

import consulo.annotation.component.ExtensionImpl
import consulo.virtualFileSystem.fileType.FileNameMatcherFactory
import consulo.virtualFileSystem.fileType.FileTypeConsumer
import consulo.virtualFileSystem.fileType.FileTypeFactory
import jakarta.inject.Inject
import org.toml.lang.psi.TomlFileType

@ExtensionImpl
class TomlFileTypeFactory @Inject constructor(private val matcherFactory: FileNameMatcherFactory) :
    FileTypeFactory() {
    override fun createFileTypes(fileTypeConsumer: FileTypeConsumer) {
        fileTypeConsumer.consume(TomlFileType, "toml")

        for (name in arrayOf("Cargo.lock", "Cargo.toml.orig", "Gopkg.lock", "Pipfile", "poetry.lock")) {
            fileTypeConsumer.consume(TomlFileType, matcherFactory.createExactFileNameMatcher(name))
        }
    }
}
