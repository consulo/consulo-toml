module org.toml.lang {
    requires consulo.annotation;
    requires consulo.component.api;
    requires consulo.application.api;
    requires consulo.project.api;
    requires consulo.module.api;
    requires consulo.language.api;
    requires consulo.language.impl;
    requires consulo.language.editor.api;
    requires consulo.color.scheme.api;
    requires consulo.language.code.style.api;
    requires consulo.language.code.style.ui.api;
    requires consulo.virtual.file.system.api;
    requires consulo.configurable.api;

    requires consulo.ui.ex.api;

    requires consulo.util.lang;

    requires kotlin.stdlib;
}