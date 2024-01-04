package com.infine.sg.tondeuse.domain;

import org.junit.jupiter.api.Assertions;

import java.util.List;

public class OrientationTests {
    record Test(Orientation original, Rotatable.Direction direction, Orientation result) {

    }

    @org.junit.jupiter.api.Test
    void shouldCorrectlyRotateOrientations() {
        final List<Test> tests = List.of(
            new Test(
                Orientation.NORTH,
                Rotatable.Direction.CLOCKWISE,
                Orientation.EAST
            ),
            new Test(
                Orientation.NORTH,
                Rotatable.Direction.ANTI_CLOCKWISE,
                Orientation.WEST
            ),
            new Test(
                Orientation.EAST,
                Rotatable.Direction.CLOCKWISE,
                Orientation.SOUTH
            ),
            new Test(
                Orientation.EAST,
                Rotatable.Direction.ANTI_CLOCKWISE,
                Orientation.NORTH
            ),
            new Test(
                Orientation.SOUTH,
                Rotatable.Direction.CLOCKWISE,
                Orientation.WEST
            ),
            new Test(
                Orientation.SOUTH,
                Rotatable.Direction.ANTI_CLOCKWISE,
                Orientation.EAST
            ),
            new Test(
                Orientation.WEST,
                Rotatable.Direction.CLOCKWISE,
                Orientation.NORTH
            ),
            new Test(
                Orientation.WEST,
                Rotatable.Direction.ANTI_CLOCKWISE,
                Orientation.SOUTH
            )
        );

        tests.forEach((final Test test) -> {
            Assertions.assertEquals(test.result, test.original.rotate(test.direction), String.format("Expected %s rotation of %s to give %s", test.direction, test.original, test.result));
        });
    }
}
