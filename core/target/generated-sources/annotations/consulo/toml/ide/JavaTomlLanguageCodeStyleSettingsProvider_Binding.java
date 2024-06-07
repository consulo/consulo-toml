package consulo.toml.ide;

import consulo.annotation.component.ComponentScope;
import consulo.annotation.component.ExtensionAPI;
import consulo.component.bind.InjectingBinding;
import consulo.language.codeStyle.setting.LanguageCodeStyleSettingsProvider;
import java.lang.Class;
import java.lang.Object;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.lang.reflect.Type;

@SuppressWarnings("ALL")
public final class JavaTomlLanguageCodeStyleSettingsProvider_Binding implements InjectingBinding {
  public Class getApiClass() {
    return LanguageCodeStyleSettingsProvider.class;
  }

  public String getApiClassName() {
    return "consulo.language.codeStyle.setting.LanguageCodeStyleSettingsProvider";
  }

  public Class getImplClass() {
    return JavaTomlLanguageCodeStyleSettingsProvider.class;
  }

  public Class getComponentAnnotationClass() {
    return ExtensionAPI.class;
  }

  public ComponentScope getComponentScope() {
    return ComponentScope.APPLICATION;
  }

  public int getComponentProfiles() {
    return 0;
  }

  public int getParametersCount() {
    return 0;
  }

  public Type[] getParameterTypes() {
    return EMPTY_TYPES;
  }

  public Object create(Object[] args) {
    return new JavaTomlLanguageCodeStyleSettingsProvider();
  }
}
