package com.infine.sg.tondeuse.mapping;

import com.infine.sg.tondeuse.domain.MowerPosition;

import java.util.Objects;

public interface MowerPositionSerialiser {
    default String serialisePosition(final MowerPosition position) {
        Objects.requireNonNull(position);

        return String.format(
            "%d %d %s",
            position.coordinate().xPosition(),
            position.coordinate().yPosition(),
            OrientationSerialiser.orientationCode(position.orientation())
        );
    }
}
