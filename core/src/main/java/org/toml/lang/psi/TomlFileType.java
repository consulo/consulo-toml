/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.lang.psi;

import consulo.language.file.LanguageFileType;
import consulo.localize.LocalizeValue;
import consulo.toml.icon.TomlIconGroup;
import consulo.toml.localize.TomlLocalize;
import consulo.ui.image.Image;
import consulo.virtualFileSystem.VirtualFile;
import consulo.virtualFileSystem.fileType.FileTypeIdentifiableByVirtualFile;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.toml.lang.TomlLanguage;

public class TomlFileType extends LanguageFileType implements FileTypeIdentifiableByVirtualFile {
    public static final TomlFileType INSTANCE = new TomlFileType();

    private TomlFileType() {
        super(TomlLanguage.INSTANCE);
    }

    @Nonnull
    @Override
    public String getId() {
        return "TOML";
    }

    @Nonnull
    @Override
    public LocalizeValue getDescription() {
        return TomlLocalize.filetypeTomlDescription();
    }

    @Nonnull
    @Override
    public String getDefaultExtension() {
        return "toml";
    }

    @Nullable
    @Override
    public Image getIcon() {
        return TomlIconGroup.toml_file();
    }

    @Override
    public String getCharset(@Nonnull VirtualFile file, @Nonnull byte[] content) {
        return "UTF-8";
    }

    @Override
    public boolean isMyFileType(@Nonnull VirtualFile file) {
        return "config".equals(file.getName()) && file.getParent() != null && ".cargo".equals(file.getParent().getName());
    }
}
