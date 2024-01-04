package com.infine.sg.tondeuse.domain;

import java.util.Map;
import java.util.stream.Stream;

/**
 * A type representing a lawn which may be mown
 */
public interface Lawn {
    // the mowers currently on the lawn, with their position
    Map<? extends Mower, MowerPosition> mowers();

    /**
     * Move the given mower, on the lawn, with the given list of movements
     * NOT thread safe, since we update the state of the lawn
     * @param mower
     * @param movements
     * @param initialPosition the initial position of the mower on the lawn which will be used if the mower is not already present on the lawn (must be valid for the lawn)
     * @return the new position of the mower
     */
    MowerPosition moveMower(final Mower mower, final Stream<MowerMovement> movements, final MowerPosition initialPosition);
}
