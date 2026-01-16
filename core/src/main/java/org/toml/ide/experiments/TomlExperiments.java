/*
 * Use of this source code is governed by the MIT license that can be
 * found in the LICENSE file.
 */

package org.toml.ide.experiments;

import consulo.application.util.registry.Registry;

public final class TomlExperiments {
    public static final String JSON_SCHEMA = "org.toml.json.schema";

    private TomlExperiments() {
    }

    public static boolean isJsonSchemaEnabled() {
        return isFeatureEnabled(JSON_SCHEMA);
    }

    private static boolean isFeatureEnabled(String registryKey) {
        return Registry.is(registryKey, false);
    }
}
