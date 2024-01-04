package com.infine.sg.tondeuse.domain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Stream;

/**
 * An implementation of a lawn which is delineated by a grid and may contain any number of mowers concurrently
 */
public class GridLawn implements Lawn {
    private final static Logger logger = LogManager.getLogger(GridLawn.class);
    private final Grid grid;
    private final Map<Mower, MowerPosition> mowers;

    private GridLawn(final Grid grid, final Map<Mower, MowerPosition> mowers) {
        this.grid = grid;
        this.mowers = mowers;
    }

    public GridLawn(final Grid grid) {
        Objects.requireNonNull(grid);

        this.grid = grid;
        this.mowers = new HashMap<>();
    }

    // the mowers currently on the lawn, with their position
    @Override
    public Map<? extends Mower, MowerPosition> mowers() {
        return this.mowers;
    }

    /**
     * Move the given mower, on the lawn, with the given list of movements
     * If the mower us not currently on the lawn, it will start at position (0,0)
     * NB the Stream of movements may not be parallelised
     * @param mower
     * @param movements
     * @param initialPosition the initial position of the mower on the lawn which will be used if the mower is not already present on the lawn (must be valid for the lawn)
     * @return the new position of the mower
     */
    @Override
    public MowerPosition moveMower(final Mower mower, final Stream<MowerMovement> movements, final MowerPosition initialPosition) {
        Objects.requireNonNull(mower);
        Objects.requireNonNull(movements);
        Objects.requireNonNull(initialPosition);
        if (!this.grid.isPositionValid(initialPosition.coordinate())) {
            GridLawn.logger.warn("Attempting to move mower on lawn with invalid initial position {}", initialPosition);
            throw new IllegalArgumentException("Initial position is not valid for lawn");
        }

        final MowerPosition currentPosition = Optional.ofNullable(this.mowers.get(mower)).orElseGet(() -> {
            GridLawn.logger.debug("Placing new mower on lawn in position {}", initialPosition);
            return initialPosition;
        });

        final MowerPosition newPosition = movements
            // ensure that we ignore any null instructions
            .filter(Objects::nonNull)
            // the reduce cannot be parallelised because the result of each movement depends on the previous position so the order of applying operations is important
            .reduce(currentPosition, (final MowerPosition lastPosition, final MowerMovement movement) -> this.moveMower(mower, lastPosition, movement), (l, r) -> r);

        this.mowers.put(mower, newPosition);

        GridLawn.logger.debug("Moved mower to position {}", newPosition);

        return newPosition;
    }

    /**
     * Move a mower, on the lawn, from its current position, with the given movement
     * @param mower (not currently necessary for the calculation but may be needed if we need to avoid other mowers during movement)
     * @param mowerPosition
     * @param movement
     * @return the new position of the mower
     */
    private MowerPosition moveMower(final Mower mower, final MowerPosition mowerPosition, final MowerMovement movement) {
        final Orientation orientation = mowerPosition.orientation();
        return switch (movement) {
            case TURN_CLOCKWISE -> new MowerPosition(orientation.rotate(Rotatable.Direction.CLOCKWISE), mowerPosition.coordinate());
            case TURN_ANTICLOCKWISE -> new MowerPosition(orientation.rotate(Rotatable.Direction.ANTI_CLOCKWISE), mowerPosition.coordinate());
            case ADVANCE -> new MowerPosition(orientation, this.advanceMower(mower, orientation, mowerPosition.coordinate()));
        };
    }

    /**
     * Advance the mower in the given direction, from the given location, as long as this is possible
     * If the movement would take the mower outside the bounds of the grid then we do not move it
     * @param mower (not currently necessary for the calculation but may be needed if we need to avoid other mowers during movement)
     * @param direction
     * @param currentCoordinate
     * @return the new position of the mower
     */
    private Grid.Coordinate advanceMower(final Mower mower, final Orientation direction, final Grid.Coordinate currentCoordinate) {
        final Grid.Coordinate newCoordinate = switch (direction) {
            case NORTH -> new Grid.Coordinate(currentCoordinate.xPosition(), currentCoordinate.yPosition() + 1);
            case EAST -> new Grid.Coordinate(currentCoordinate.xPosition() + 1, currentCoordinate.yPosition());
            case WEST -> new Grid.Coordinate(currentCoordinate.xPosition() - 1, currentCoordinate.yPosition());
            case SOUTH -> new Grid.Coordinate(currentCoordinate.xPosition(), currentCoordinate.yPosition() - 1);
        };
        return this.grid.isPositionValid(newCoordinate) ? newCoordinate : currentCoordinate;
    }
}
