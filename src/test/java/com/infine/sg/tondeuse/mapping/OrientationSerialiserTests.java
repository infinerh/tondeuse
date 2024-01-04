package com.infine.sg.tondeuse.mapping;

import com.infine.sg.tondeuse.domain.Orientation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class OrientationSerialiserTests {
    @Test
    void shouldCorrectlySerialiseOrientation() {
        Arrays.stream(Orientation.values())
            .forEach((final Orientation orientation) -> {
                final String text = OrientationSerialiser.orientationCode(orientation);

                switch (orientation) {
                    case NORTH -> Assertions.assertEquals("N", text);
                    case EAST -> Assertions.assertEquals("E", text);
                    case WEST -> Assertions.assertEquals("W", text);
                    case SOUTH -> Assertions.assertEquals("S", text);
                }
            });
    }
}
