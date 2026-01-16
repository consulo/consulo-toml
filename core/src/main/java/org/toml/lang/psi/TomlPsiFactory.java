/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.lang.psi;

import consulo.language.psi.PsiElement;
import consulo.language.psi.PsiFileFactory;
import consulo.language.psi.PsiParserFacade;
import consulo.language.psi.util.PsiTreeUtil;
import consulo.project.Project;
import consulo.util.lang.LocalTimeCounter;
import jakarta.annotation.Nonnull;

public class TomlPsiFactory {
    private final Project project;
    private final boolean markGenerated;

    public TomlPsiFactory(@Nonnull Project project) {
        this(project, true);
    }

    public TomlPsiFactory(@Nonnull Project project, boolean markGenerated) {
        this.project = project;
        this.markGenerated = markGenerated;
    }

    @Nonnull
    private TomlFile createFile(@Nonnull CharSequence text) {
        return (TomlFile) PsiFileFactory.getInstance(project)
                .createFileFromText(
                        "DUMMY.toml",
                        TomlFileType.INSTANCE,
                        text,
                        LocalTimeCounter.currentTime(), // default value
                        false, // default value (eventSystemEnabled)
                        markGenerated // true by default (markAsCopy)
                );
    }

    private <T extends TomlElement> T createFromText(@Nonnull String code, @Nonnull Class<T> clazz) {
        return PsiTreeUtil.findChildOfType(createFile(code), clazz, true);
    }

    @Nonnull
    public PsiElement createNewline() {
        return createWhitespace("\n");
    }

    @Nonnull
    public PsiElement createWhitespace(@Nonnull String ws) {
        return PsiParserFacade.getInstance(project).createWhiteSpaceFromText(ws);
    }

    @Nonnull
    public TomlLiteral createLiteral(@Nonnull String value) {
        // If you're creating a string value, like `serde = "1.0.90"` make sure that the `value` parameter actually
        // contains the quote in the beginning and the end. E.g.: `createValue("\"1.0.90\"")`
        TomlLiteral result = createFromText("dummy = " + value, TomlLiteral.class);
        if (result == null) {
            throw new IllegalStateException("Failed to create TomlLiteral");
        }
        return result;
    }

    @Nonnull
    public TomlKeySegment createKeySegment(@Nonnull String key) {
        TomlKeySegment result = createFromText(key + " = \"dummy\"", TomlKeySegment.class);
        if (result == null) {
            throw new IllegalStateException("Failed to create TomlKeySegment");
        }
        return result;
    }

    @Nonnull
    public TomlKey createKey(@Nonnull String key) {
        TomlKey result = createFromText(key + " = \"dummy\"", TomlKey.class);
        if (result == null) {
            throw new IllegalStateException("Failed to create TomlKey");
        }
        return result;
    }

    @Nonnull
    public TomlKeyValue createKeyValue(@Nonnull String text) {
        // Make sure that `text` includes the equals sign in the middle like so: "serde = \"1.0.90\""
        TomlKeyValue result = createFromText(text, TomlKeyValue.class);
        if (result == null) {
            throw new IllegalStateException("Failed to create TomlKeyValue");
        }
        return result;
    }

    @Nonnull
    public TomlKeyValue createKeyValue(@Nonnull String key, @Nonnull String value) {
        TomlKeyValue result = createFromText(key + " = " + value, TomlKeyValue.class);
        if (result == null) {
            throw new IllegalStateException("Failed to create TomlKeyValue");
        }
        return result;
    }

    @Nonnull
    public TomlTable createTable(@Nonnull String name) {
        TomlTable result = createFromText("[" + name + "]", TomlTable.class);
        if (result == null) {
            throw new IllegalStateException("Failed to create TomlTable");
        }
        return result;
    }

    @Nonnull
    public TomlTableHeader createTableHeader(@Nonnull String name) {
        TomlTableHeader result = createFromText("[" + name + "]", TomlTableHeader.class);
        if (result == null) {
            throw new IllegalStateException("Failed to create TomlTableHeader");
        }
        return result;
    }

    @Nonnull
    public TomlArray createArray(@Nonnull String contents) {
        TomlArray result = createFromText("dummy = [" + contents + "]", TomlArray.class);
        if (result == null) {
            throw new IllegalStateException("Failed to create TomlArray");
        }
        return result;
    }

    @Nonnull
    public TomlInlineTable createInlineTable(@Nonnull String contents) {
        TomlInlineTable result = createFromText("dummy = {" + contents + "}", TomlInlineTable.class);
        if (result == null) {
            throw new IllegalStateException("Failed to create TomlInlineTable");
        }
        return result;
    }
}
