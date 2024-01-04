package com.infine.sg.tondeuse.io;

import com.infine.sg.tondeuse.domain.Grid;
import com.infine.sg.tondeuse.domain.MowerMovement;
import com.infine.sg.tondeuse.domain.MowerPosition;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * A type which may return the initial configuration of a lawn and the mowers and their movement instructions to be used on it
 */
public interface LawnStreamInstructionsStreamer {
    record MowerInstructions(MowerPosition initialPosition, Stream<MowerMovement> movements) {
        public MowerInstructions {
            Objects.requireNonNull(initialPosition);
            Objects.requireNonNull(movements);
        }
    }
    record LawnInstructionsStream(Grid grid, Stream<MowerInstructions> mowers) {
        public LawnInstructionsStream {
            Objects.requireNonNull(grid);
            Objects.requireNonNull(mowers);
        }
    }

    /**
     * Stream instructions for a lawn and its mowers
     * @return
     */
    LawnInstructionsStream instructions();
}
