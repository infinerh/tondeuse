package com.infine.sg.tondeuse.mapping;

import com.infine.sg.tondeuse.domain.Orientation;

import java.util.Objects;

public interface OrientationSerialiser {
    /**
     * Serialise an orientation to its character representation
     * @param orientation
     * @return
     */
    static String orientationCode(final Orientation orientation) {
        Objects.requireNonNull(orientation);

        return switch (orientation) {
            case NORTH -> "N";
            case EAST -> "E";
            case WEST -> "W";
            case SOUTH -> "S";
        };
    }
}
