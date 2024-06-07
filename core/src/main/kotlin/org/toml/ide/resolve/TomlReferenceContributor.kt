package org.toml.ide.resolve

import consulo.language.impl.psi.path.WebReference
import consulo.language.pattern.PlatformPatterns.psiElement
import consulo.language.psi.*
import consulo.language.util.ProcessingContext
import org.toml.lang.psi.TomlLiteral
import org.toml.lang.psi.ext.TomlLiteralKind
import org.toml.lang.psi.ext.kind

abstract class TomlReferenceContributor : PsiReferenceContributor() {
    override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {
        registrar.registerReferenceProvider(
            psiElement(TomlLiteral::class.java),
            TomlWebReferenceProvider(),
            PsiReferenceRegistrar.LOWER_PRIORITY
        )
    }
}

private class TomlWebReferenceProvider : PsiReferenceProvider() {

    // web references do not point to any real PsiElement
    override fun acceptsTarget(target: PsiElement): Boolean = false

    override fun getReferencesByElement(element: PsiElement, context: ProcessingContext): Array<PsiReference> {
        val kind = (element as? TomlLiteral)?.kind as? TomlLiteralKind.String ?: return PsiReference.EMPTY_ARRAY
        if (!element.textContains(':')) return PsiReference.EMPTY_ARRAY
        val textValue = kind.value ?: return PsiReference.EMPTY_ARRAY

        return if (isWebReferenceUrl(textValue)) {
            arrayOf(WebReference(element, textValue))
        } else {
            PsiReference.EMPTY_ARRAY
        }
    }

    fun isWebReferenceUrl(url: String): Boolean {
        return url.startsWith("http://") || url.startsWith("https://") || url.startsWith("about:") || url.startsWith("mailto:")
    }
}
