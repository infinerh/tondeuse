package com.infine.sg.tondeuse.domain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

/**
 * A grid whose origin is (0,0) and whose top right corner is defined by maxX and maxY
 * @param maxX the right limit of the lawn (must be >= 0)
 * @param maxY the top limit of the lawn (must be >= 0)
 */
public record Grid(int maxX, int maxY) {
    private final static Logger logger = LogManager.getLogger(Grid.class);

    public Grid {
        if (maxX < 0) {
            Grid.logger.warn("Attempt to instantiate Grid with negative width");
            throw new IllegalArgumentException("Grid must have positive width");
        }
        if (maxY < 0) {
            Grid.logger.warn("Attempt to instantiate Grid with negative height");
            throw new IllegalArgumentException("Grid must have positive height");
        }
    }

    /**
     * A coordinate representing a position on a grid
     * @param xPosition
     * @param yPosition
     */
    public record Coordinate(int xPosition, int yPosition) {
        public Coordinate {
            if (xPosition < 0) {
                throw new IllegalArgumentException("X axis value must be positive");
            }
            if (yPosition < 0) {
                throw new IllegalArgumentException("Y axis value must be positive");
            }
        }
    }

    /**
     * Validate that a position is valid for the lawn
     * @param coordinate
     * @return
     */
    boolean isPositionValid(final Grid.Coordinate coordinate) {
        Objects.requireNonNull(coordinate);

        final int x = coordinate.xPosition();
        final int y = coordinate.yPosition();

        return x >= 0
            && x <= this.maxX()
            && y >= 0
            && y <= this.maxY()
            ;
    }
}
