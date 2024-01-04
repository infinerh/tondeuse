package com.infine.sg.tondeuse.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class GridTests {
    private record Coordinate(int x, int y) {

    }

    @Test
    void shouldValidatePositions() {
        final int maxX = 10;
        final int maxY = 20;
        final Grid grid = new Grid(maxX, maxY);

        final List<Coordinate> invalidCoordinates = List.of(
            new Coordinate(-3, maxY - 2),
            new Coordinate(maxX - 2, -1),
            new Coordinate(-2, -1)
        );

        invalidCoordinates.forEach((final Coordinate coordinate) -> {
            Assertions.assertThrows(IllegalArgumentException.class, () -> grid.isPositionValid(new Grid.Coordinate(coordinate.x, coordinate.y)), String.format("Expected invalid coordinate %s to throw exception", coordinate));
        });

        final List<Grid.Coordinate> badCoordinates = List.of(
            new Grid.Coordinate(maxX + 1, maxY - 2),
            new Grid.Coordinate(maxX - 2, maxY + 1),
            new Grid.Coordinate(maxX + 3, maxY + 1)
        );

        badCoordinates.forEach((final Grid.Coordinate coordinate) -> {
            Assertions.assertFalse(grid.isPositionValid(coordinate), String.format("Expected bad coordinate %s to be rejected", coordinate));
        });

        final List<Grid.Coordinate> goodCoordinates = List.of(
            new Grid.Coordinate(0, maxY - 2),
            new Grid.Coordinate(maxX - 2, 0),
            new Grid.Coordinate(0, 0),
            new Grid.Coordinate(maxX - 1, maxY - 2),
            new Grid.Coordinate(maxX - 2, maxY - 1)
        );

        goodCoordinates.forEach((final Grid.Coordinate coordinate) -> {
            Assertions.assertTrue(grid.isPositionValid(coordinate), String.format("Expected good coordinate %s to be validated", coordinate));
        });
    }
}
