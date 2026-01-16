/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.lang.psi;

import consulo.language.psi.ContributedReferenceHost;
import consulo.language.psi.NavigatablePsiElement;
import consulo.language.psi.PsiNamedElement;

/**
 * It's possible to use PsiReferenceContributor to inject references
 * into TomlKeySegment from third-party plugins.
 */
public interface TomlKeySegment extends TomlElement, ContributedReferenceHost, PsiNamedElement, NavigatablePsiElement {
}
