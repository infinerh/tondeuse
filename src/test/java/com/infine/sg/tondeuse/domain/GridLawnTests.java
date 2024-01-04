package com.infine.sg.tondeuse.domain;

import org.junit.jupiter.api.Assertions;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class GridLawnTests {
    private record Test(Mower mower, MowerPosition initialPosition, MowerMovement move, MowerPosition result) {

    }

    @org.junit.jupiter.api.Test
    void shouldCorrectlyMoveMowerOnLawn() {
        final GridLawn lawn = new GridLawn(new Grid(5, 5));
        final Mower mower1 = new Mower() {};
        final Mower mower2 = new Mower() {};

        final MowerPosition mower1InitialPosition = new MowerPosition(Orientation.NORTH, new Grid.Coordinate(1, 2));
        final MowerPosition mower2InitialPosition = new MowerPosition(Orientation.EAST, new Grid.Coordinate(3, 3));

        final List<Test> tests = List.of(
            new Test(
                mower1,
                mower1InitialPosition,
                MowerMovement.TURN_ANTICLOCKWISE,
                new MowerPosition(Orientation.WEST, mower1InitialPosition.coordinate())
            ),
            new Test(
                mower1,
                mower1InitialPosition,
                MowerMovement.ADVANCE,
                new MowerPosition(Orientation.WEST, new Grid.Coordinate(0, 2))
            ),
            new Test(
                mower1,
                mower1InitialPosition,
                MowerMovement.TURN_ANTICLOCKWISE,
                new MowerPosition(Orientation.SOUTH, new Grid.Coordinate(0, 2))
            ),
            new Test(
                mower1,
                mower1InitialPosition,
                MowerMovement.ADVANCE,
                new MowerPosition(Orientation.SOUTH, new Grid.Coordinate(0, 1))
            ),
            new Test(
                mower1,
                mower1InitialPosition,
                MowerMovement.TURN_ANTICLOCKWISE,
                new MowerPosition(Orientation.EAST, new Grid.Coordinate(0, 1))
            ),
            new Test(
                mower1,
                mower1InitialPosition,
                MowerMovement.ADVANCE,
                new MowerPosition(Orientation.EAST, new Grid.Coordinate(1, 1))
            ),
            new Test(
                mower1,
                mower1InitialPosition,
                MowerMovement.TURN_ANTICLOCKWISE,
                new MowerPosition(Orientation.NORTH, new Grid.Coordinate(1, 1))
            ),
            new Test(
                mower1,
                mower1InitialPosition,
                MowerMovement.ADVANCE,
                new MowerPosition(Orientation.NORTH, new Grid.Coordinate(1, 2))
            ),
            new Test(
                mower1,
                mower1InitialPosition,
                MowerMovement.ADVANCE,
                new MowerPosition(Orientation.NORTH, new Grid.Coordinate(1, 3))
            ),

            new Test(
                mower2,
                mower2InitialPosition,
                MowerMovement.ADVANCE,
                new MowerPosition(Orientation.EAST, new Grid.Coordinate(4, 3))
            ),
            new Test(
                mower2,
                mower2InitialPosition,
                MowerMovement.ADVANCE,
                new MowerPosition(Orientation.EAST, new Grid.Coordinate(5, 3))
            ),
            new Test(
                mower2,
                mower2InitialPosition,
                MowerMovement.TURN_CLOCKWISE,
                new MowerPosition(Orientation.SOUTH, new Grid.Coordinate(5, 3))
            ),
            new Test(
                mower2,
                mower2InitialPosition,
                MowerMovement.ADVANCE,
                new MowerPosition(Orientation.SOUTH, new Grid.Coordinate(5, 2))
            ),
            new Test(
                mower2,
                mower2InitialPosition,
                MowerMovement.ADVANCE,
                new MowerPosition(Orientation.SOUTH, new Grid.Coordinate(5, 1))
            ),
            new Test(
                mower2,
                mower2InitialPosition,
                MowerMovement.TURN_CLOCKWISE,
                new MowerPosition(Orientation.WEST, new Grid.Coordinate(5, 1))
            ),
            new Test(
                mower2,
                mower2InitialPosition,
                MowerMovement.ADVANCE,
                new MowerPosition(Orientation.WEST, new Grid.Coordinate(4, 1))
            ),
            new Test(
                mower2,
                mower2InitialPosition,
                MowerMovement.TURN_CLOCKWISE,
                new MowerPosition(Orientation.NORTH, new Grid.Coordinate(4, 1))
            ),
            new Test(
                mower2,
                mower2InitialPosition,
                MowerMovement.TURN_CLOCKWISE,
                new MowerPosition(Orientation.EAST, new Grid.Coordinate(4, 1))
            ),
            new Test(
                mower2,
                mower2InitialPosition,
                MowerMovement.ADVANCE,
                new MowerPosition(Orientation.EAST, new Grid.Coordinate(5, 1))
            )
        );

        tests.forEach((final Test test) -> {
            final MowerPosition newPosition = lawn.moveMower(test.mower, Stream.of(test.move), test.initialPosition);

            Assertions.assertEquals(test.result, newPosition, "Expected correct move result to be returned");

            Optional.ofNullable(lawn.mowers().get(test.mower))
                .map((final MowerPosition position) -> {
                    Assertions.assertEquals(test.result, position, "Expected mower on lawn to retain correct position after move");
                    return true;
                })
                .orElseGet(() -> {
                    Assertions.fail("Expected mower to be on lawn after move");
                    return false;
                });
        });
    }
}
