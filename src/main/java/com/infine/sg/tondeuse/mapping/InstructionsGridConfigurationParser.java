package com.infine.sg.tondeuse.mapping;

import com.infine.sg.tondeuse.domain.Grid;

public interface InstructionsGridConfigurationParser {
    /**
     * Parse a grid configuration from a string input
     * @param input
     * @return
     */
    default Grid gridFromString(final String input) {
        final String[] parameters = input.split(" ");
        try {
            return new Grid(Integer.parseInt(parameters[0]), Integer.parseInt(parameters[1]));
        } catch (Exception error) {
            throw new IllegalArgumentException("Invalid format for grid configuration: " + input, error);
        }
    }
}
