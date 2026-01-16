/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.lang.psi;

import consulo.language.psi.ContributedReferenceHost;
import consulo.language.psi.PsiLanguageInjectionHost;

/**
 * It's possible to use PsiReferenceContributor to inject references
 * into TomlLiteral from third-party plugins.
 */
public interface TomlLiteral extends TomlValue, ContributedReferenceHost, PsiLanguageInjectionHost {
}
