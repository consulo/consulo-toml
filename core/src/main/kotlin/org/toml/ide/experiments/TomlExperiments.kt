/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.ide.experiments

import consulo.application.util.registry.Registry

object TomlExperiments {
    const val JSON_SCHEMA = "org.toml.json.schema"

    val isJsonSchemaEnabled: Boolean
      get() = isFeatureEnabled(JSON_SCHEMA)

    @Suppress("SameParameterValue")
    private fun isFeatureEnabled(registryKey: String): Boolean =
        Registry.`is`(registryKey, false)
}
