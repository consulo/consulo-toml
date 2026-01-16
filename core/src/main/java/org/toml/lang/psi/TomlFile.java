/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.lang.psi;

import consulo.language.file.FileViewProvider;
import consulo.language.impl.psi.PsiFileBase;
import consulo.virtualFileSystem.fileType.FileType;
import jakarta.annotation.Nonnull;
import org.toml.lang.TomlLanguage;

public class TomlFile extends PsiFileBase {
    public TomlFile(@Nonnull FileViewProvider viewProvider) {
        super(viewProvider, TomlLanguage.INSTANCE);
    }

    @Nonnull
    @Override
    public FileType getFileType() {
        return TomlFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "TOML File";
    }
}
