/*
 * This file is part of ViaVersion - https://github.com/ViaVersion/ViaVersion
 * Copyright (C) 2016-2022 ViaVersion and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.viaversion.viaversion.protocols.protocol1_19to1_18_2.storage;

import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.api.connection.StorableObject;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.HashMap;
import java.util.Map;

public final class DimensionRegistryStorage implements StorableObject {

    private Map<CompoundTag, String> dimensions;
    private Map<String, String> velocityHack;

    public @Nullable String dimensionKey(final CompoundTag dimensionData) {
        return velocityHack.get(createVelocityHackKey(dimensionData));
    }

    public void setDimensions(final Map<CompoundTag, String> dimensions) {
        this.dimensions = dimensions;

        this.velocityHack = new HashMap<>();
        for (Map.Entry<CompoundTag, String> entry : dimensions.entrySet()) {
            // Captures all differences in the four vanilla dimensions
            velocityHack.put(createVelocityHackKey(entry.getKey()), entry.getValue());
        }
    }

    private String createVelocityHackKey(CompoundTag tag) {
        Object first = tag.get("effects");
        Object second = tag.get("has_ceiling");
        return "" + first + second;
    }

    public Map<CompoundTag, String> dimensions() {
        return dimensions;
    }

    @Override
    public boolean clearOnServerSwitch() {
        return false;
    }
}
