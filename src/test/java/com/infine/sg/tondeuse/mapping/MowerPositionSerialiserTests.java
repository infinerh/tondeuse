package com.infine.sg.tondeuse.mapping;

import com.infine.sg.tondeuse.domain.Grid;
import com.infine.sg.tondeuse.domain.MowerPosition;
import com.infine.sg.tondeuse.domain.Orientation;
import org.junit.jupiter.api.Assertions;

import java.util.List;

public class MowerPositionSerialiserTests {
    private record Test(MowerPosition position, String expectedSerialisation) {

    }

    @org.junit.jupiter.api.Test
    void shouldCorrectlySerialiseMowerPosition() {
        final List<Test> tests = List.of(
            new Test(
                new MowerPosition(Orientation.NORTH, new Grid.Coordinate(1, 3)),
                "1 3 N"
            ),
            new Test(
                new MowerPosition(Orientation.EAST, new Grid.Coordinate(5, 1)),
                "5 1 E"
            )
        );

        final MowerPositionSerialiser serialiser = new MowerPositionSerialiser() {};

        tests.forEach((final Test test) -> {
            final String position = serialiser.serialisePosition(test.position);

            Assertions.assertEquals(test.expectedSerialisation, position, String.format("Expected position %s to be correctly deserialised", test.position));
        });
    }
}
