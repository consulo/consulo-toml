module org.toml.lang {
    requires consulo.language.api;
    requires consulo.language.impl;
    requires consulo.language.editor.api;
    requires consulo.language.code.style.api;
    requires consulo.language.code.style.ui.api;

    requires consulo.application.api;
    requires consulo.code.editor.api;
    requires consulo.color.scheme.api;
    requires consulo.configurable.api;
    requires consulo.disposer.api;
    requires consulo.document.api;
    requires consulo.navigation.api;
    requires consulo.project.api;
    requires consulo.ui.api;
    requires consulo.ui.ex.api;
    requires consulo.virtual.file.system.api;
    requires consulo.util.io;
    requires consulo.util.lang;

    exports consulo.toml.icon;
    exports consulo.toml.ide;
    exports consulo.toml.ide.annotator;
    exports org.toml.ide;
    exports org.toml.ide.annotator;
    exports org.toml.ide.colors;
    exports org.toml.ide.experiments;
    exports org.toml.ide.folding;
    exports org.toml.ide.formatter;
    exports org.toml.ide.formatter.impl;
    exports org.toml.ide.formatter.settings;
    exports org.toml.ide.inspections;
    exports org.toml.ide.intentions;
    exports org.toml.ide.resolve;
    exports org.toml.ide.search;
    exports org.toml.ide.todo;
    exports org.toml.ide.wordSelection;
    exports org.toml.lang;
    exports org.toml.lang.lexer;
    exports org.toml.lang.parse;
    exports org.toml.lang.psi;
    exports org.toml.lang.psi.ext;
    exports org.toml.lang.psi.impl;
}
