package com.infine.sg.tondeuse.mapping;

import com.infine.sg.tondeuse.domain.Grid;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InstructionsGridConfigurationParserTests {
    @Test
    void shouldCorrectlyParseGridDefinition() {
        final String gridDefinition = "5 5";
        final Grid expectedGrid = new Grid(5, 5);

        Assertions.assertEquals(expectedGrid, (new InstructionsGridConfigurationParser() {}).gridFromString(gridDefinition), "Expected correct grid definition");
    }
}
