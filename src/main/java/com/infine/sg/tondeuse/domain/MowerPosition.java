package com.infine.sg.tondeuse.domain;

import java.util.Objects;

/**
 * The current position of a mower, on a lawn
 * @param orientation
 * @param coordinate
 */
public record MowerPosition(Orientation orientation, Grid.Coordinate coordinate) {
    public MowerPosition {
        Objects.requireNonNull(orientation);
        Objects.requireNonNull(coordinate);
    }
}
