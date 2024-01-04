package com.infine.sg.tondeuse.mapping;

import com.infine.sg.tondeuse.domain.Grid;
import com.infine.sg.tondeuse.domain.MowerMovement;
import com.infine.sg.tondeuse.domain.MowerPosition;
import com.infine.sg.tondeuse.domain.Orientation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class InstructionsMowerConfigurationParserTests {
    final InstructionsMowerConfigurationParser parser = new InstructionsMowerConfigurationParser() {};
    private record OrientationTest(String orientationText, Orientation expectedOrientation) {

    }

    @Test
    void shouldCorrectlyParseOrientation() {
        final List<OrientationTest> tests = List.of(
            new OrientationTest(
                "N",
                Orientation.NORTH
            ),
            new OrientationTest(
                "E",
                Orientation.EAST
            ),
            new OrientationTest(
                "S",
                Orientation.SOUTH
            ),
            new OrientationTest(
                "W",
                Orientation.WEST
            )
        );

        tests.forEach((final OrientationTest test) -> {
            final Orientation orientation = this.parser.orientationFromString(test.orientationText);

            Assertions.assertEquals(test.expectedOrientation, orientation, "Expected orientation to be correctly parsed");
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> this.parser.orientationFromString("D"), "Expected invalid orientation input to throw exception");
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.parser.orientationFromString("North"), "Expected invalid orientation input to throw exception");
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.parser.orientationFromString(""), "Expected invalid orientation input to throw exception");
    }

    private record PositionTest(String positionText, MowerPosition expectedPosition) {

    }

    @Test
    void shouldCorrectlyParseInitialPosition() {
        final List<PositionTest> tests = List.of(
            new PositionTest(
                "1 1 N",
                new MowerPosition(Orientation.NORTH, new Grid.Coordinate(1, 1))
            ),
            new PositionTest(
                "12 21 W",
                new MowerPosition(Orientation.WEST, new Grid.Coordinate(12, 21))
            )
        );

        tests.forEach((final PositionTest test) -> {
            final MowerPosition position = this.parser.positionFromString(test.positionText);

            Assertions.assertEquals(test.expectedPosition, position, "Expected position to be correctly parsed");
        });

        final List<String> badPositions = List.of(
            "",
            "1 1 T",
            "1 S 1",
            "S 1 1",
            "1.2 1 S",
            "1 1.3 S",
            "-1 1 S",
            "1 -1 S",
            "1, 1, S"
        );

        badPositions.forEach((final String positionText) -> {
            Assertions.assertThrows(IllegalArgumentException.class, () -> this.parser.positionFromString(positionText), String.format("Expected bad position input `%s` to throw exception", positionText));
        });
    }

    private record MovementTest(int movementCharacter, MowerMovement expectedMove) {

    }

    @Test
    void shouldCorrectlyParseMove() {
        final List<MovementTest> tests = List.of(
            new MovementTest(
                'G',
                MowerMovement.TURN_ANTICLOCKWISE
            ),
            new MovementTest(
                'D',
                MowerMovement.TURN_CLOCKWISE
            ),
            new MovementTest(
                'A',
                MowerMovement.ADVANCE
            )
        );

        tests.forEach((final MovementTest test) -> {
            final MowerMovement move = this.parser.movementFromCharacter(test.movementCharacter);

            Assertions.assertEquals(test.expectedMove, move, "Expected move to be correctly parsed");
        });

        final List<Integer> badMovements = List.of(
            -35,
            41,
            97,
            103,
            100,
            9898679
        );

        badMovements.forEach((final Integer movementCharacter) -> {
            Assertions.assertThrows(IllegalArgumentException.class, () -> this.parser.movementFromCharacter(movementCharacter), String.format("Expected bad move input %d to throw exception", movementCharacter));
        });
    }
}
