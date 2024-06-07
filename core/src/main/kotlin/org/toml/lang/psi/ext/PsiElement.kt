/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.lang.psi.ext

import consulo.language.ast.IElementType
import consulo.language.psi.PsiElement
import consulo.language.psi.PsiUtilCore

val PsiElement.elementType: IElementType get() = PsiUtilCore.getElementType(this)
