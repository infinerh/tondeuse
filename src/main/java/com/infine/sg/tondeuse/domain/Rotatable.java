package com.infine.sg.tondeuse.domain;

/**
 * A type which may be rotated in a direction
 * @param <T> the orientation type
 */
public interface Rotatable<T> {
    enum Direction {
        CLOCKWISE,
        ANTI_CLOCKWISE;
    }

    /**
     * Rotate the item in the given direction
     * @param direction
     * @return the new orientation
     */
    T rotate(final Direction direction);
}
