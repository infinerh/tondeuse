package com.infine.sg.tondeuse.io;

import com.infine.sg.tondeuse.domain.Grid;
import com.infine.sg.tondeuse.domain.MowerMovement;
import com.infine.sg.tondeuse.domain.MowerPosition;
import com.infine.sg.tondeuse.domain.Orientation;
import org.junit.jupiter.api.Assertions;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.IntStream;

public class LawnInputStreamParserTests {
    private record Test(MowerPosition initialPosition, List<MowerMovement> movements) {

    }

    @org.junit.jupiter.api.Test
    void shouldCorrectlyParseInstructions() {
        final String inputInstructions = "5 5\n1 2 N\nGAGAGAGAA\n3 3 E\nAADAADADDA";
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(inputInstructions.getBytes(StandardCharsets.UTF_8))))) {
            final LawnInputStreamParser parser = new LawnInputStreamParser(reader);
            final LawnStreamInstructionsStreamer.LawnInstructionsStream instructions = parser.instructions();

            Assertions.assertEquals(5, instructions.grid().maxX(), "Expected grid from instructions to be correct width");
            Assertions.assertEquals(5, instructions.grid().maxY(), "Expected grid from instructions to be correct height");

            final List<LawnStreamInstructionsStreamer.MowerInstructions> mowers = instructions.mowers().toList();

            Assertions.assertEquals(2, mowers.size(), "Expected instructions for 2 mowers");

            final List<Test> tests = List.of(
                new Test(
                    new MowerPosition(Orientation.NORTH, new Grid.Coordinate(1, 2)),
                    List.of(
                        MowerMovement.TURN_ANTICLOCKWISE,
                        MowerMovement.ADVANCE,
                        MowerMovement.TURN_ANTICLOCKWISE,
                        MowerMovement.ADVANCE,
                        MowerMovement.TURN_ANTICLOCKWISE,
                        MowerMovement.ADVANCE,
                        MowerMovement.TURN_ANTICLOCKWISE,
                        MowerMovement.ADVANCE,
                        MowerMovement.ADVANCE
                    )
                ),
                new Test(
                    new MowerPosition(Orientation.EAST, new Grid.Coordinate(3, 3)),
                    List.of(
                        MowerMovement.ADVANCE,
                        MowerMovement.ADVANCE,
                        MowerMovement.TURN_CLOCKWISE,
                        MowerMovement.ADVANCE,
                        MowerMovement.ADVANCE,
                        MowerMovement.TURN_CLOCKWISE,
                        MowerMovement.ADVANCE,
                        MowerMovement.TURN_CLOCKWISE,
                        MowerMovement.TURN_CLOCKWISE,
                        MowerMovement.ADVANCE
                    )
                )
            );

            IntStream.range(0, mowers.size() - 1).forEach((int index) -> {
                final LawnStreamInstructionsStreamer.MowerInstructions mower = mowers.get(index);
                final Test test = tests.get(index);

                if (test != null) {
                    Assertions.assertEquals(test.initialPosition, mower.initialPosition(), String.format("Expected initial position of mower #%d to be parsed correctly", index));

                    final List<MowerMovement> moves = mower.movements().toList();

                    Assertions.assertEquals(test.movements.size(), moves.size(), String.format("Expected correct number of moves to be deserialised for mower #%d", index));
                    Assertions.assertEquals(test.movements, moves, String.format("Expected correct moves to be deserialised for mower #%d", index));
                }
            });
        } catch (Exception error) {
            Assertions.fail("Failed IO", error);
        }
    }
}
