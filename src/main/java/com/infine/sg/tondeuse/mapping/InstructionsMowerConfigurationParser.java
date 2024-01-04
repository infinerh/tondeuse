package com.infine.sg.tondeuse.mapping;

import com.infine.sg.tondeuse.domain.Grid;
import com.infine.sg.tondeuse.domain.MowerMovement;
import com.infine.sg.tondeuse.domain.MowerPosition;
import com.infine.sg.tondeuse.domain.Orientation;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface InstructionsMowerConfigurationParser {
    /**
     * Parse a mower configuration from a string input
     * @param input
     * @return
     */
    default MowerPosition positionFromString(final String input) {
        final String[] parameters = input.split(" ");
        try {
            return new MowerPosition(this.orientationFromString(parameters[2]), new Grid.Coordinate(Integer.parseInt(parameters[0]), Integer.parseInt(parameters[1])));
        } catch (Exception error) {
            throw new IllegalArgumentException("Invalid format for mower configuration: " + input, error);
        }
    }

    // the character representation used for left rotation
    int LEFT_ROTATION_CHARACTER = 71; // G
    // the character representation used for left rotation
    int RIGHT_ROTATION_CHARACTER = 68; // D
    // the character representation used for left rotation
    int MOVE_CHARACTER = 65; // A

    /**
     * Parse a mower instruction from a character input
     * @param input
     * @return
     */
    default MowerMovement movementFromCharacter(final int input) {
        return switch (input) {
            case InstructionsMowerConfigurationParser.LEFT_ROTATION_CHARACTER -> MowerMovement.TURN_ANTICLOCKWISE;
            case InstructionsMowerConfigurationParser.RIGHT_ROTATION_CHARACTER -> MowerMovement.TURN_CLOCKWISE;
            case InstructionsMowerConfigurationParser.MOVE_CHARACTER -> MowerMovement.ADVANCE;
            default -> throw new IllegalArgumentException("Unrecognised mower instruction: " + Character.toString(input));
        };
    }

    // a cache mapping codes to the appropriate orientation they represent
    Map<String, Orientation> ORIENTATION_CODES = Arrays.stream(Orientation.values())
        .collect(Collectors.toMap(OrientationSerialiser::orientationCode, Function.identity()));

    /**
     * Transform a String code into the appropriate orientation
     * @param input
     * @return
     */
    default Orientation orientationFromString(final String input) {
        return Optional.ofNullable(InstructionsMowerConfigurationParser.ORIENTATION_CODES.get(input)).orElseThrow(() -> new IllegalArgumentException("Unrecognised orientation code: " + input));
    }
}
