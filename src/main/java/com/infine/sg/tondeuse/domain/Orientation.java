package com.infine.sg.tondeuse.domain;

import java.util.Objects;

/**
 * The direction an element may be facing
 */
public enum Orientation implements Rotatable<Orientation> {
    NORTH {
        /**
         * Rotate the item in the given direction
         * @param direction
         * @return the new orientation
         */
        @Override
        public Orientation rotate(final Direction direction) {
            Objects.requireNonNull(direction);
            return switch (direction) {
                case CLOCKWISE -> Orientation.EAST;
                case ANTI_CLOCKWISE -> Orientation.WEST;
            };
        }
    },
    EAST {
        /**
         * Rotate the item in the given direction
         * @param direction
         * @return the new orientation
         */
        @Override
        public Orientation rotate(final Direction direction) {
            Objects.requireNonNull(direction);
            return switch (direction) {
                case CLOCKWISE -> Orientation.SOUTH;
                case ANTI_CLOCKWISE -> Orientation.NORTH;
            };
        }
    },
    WEST {
        /**
         * Rotate the item in the given direction
         * @param direction
         * @return the new orientation
         */
        @Override
        public Orientation rotate(final Direction direction) {
            Objects.requireNonNull(direction);
            return switch (direction) {
                case CLOCKWISE -> Orientation.NORTH;
                case ANTI_CLOCKWISE -> Orientation.SOUTH;
            };
        }
    },
    SOUTH {
        /**
         * Rotate the item in the given direction
         * @param direction
         * @return the new orientation
         */
        @Override
        public Orientation rotate(final Direction direction) {
            Objects.requireNonNull(direction);
            return switch (direction) {
                case CLOCKWISE -> Orientation.WEST;
                case ANTI_CLOCKWISE -> Orientation.EAST;
            };
        }
    };
}
