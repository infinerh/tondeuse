package com.infine.sg.tondeuse;

import com.infine.sg.tondeuse.domain.GridLawn;
import com.infine.sg.tondeuse.domain.Lawn;
import com.infine.sg.tondeuse.domain.Mower;
import com.infine.sg.tondeuse.domain.MowerPosition;
import com.infine.sg.tondeuse.io.LawnInputStreamParser;
import com.infine.sg.tondeuse.io.LawnStreamInstructionsStreamer;
import com.infine.sg.tondeuse.mapping.MowerPositionSerialiser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

public class MowersFileHandler implements MowersHandler, MowerPositionSerialiser {
    private final static Logger logger = LogManager.getLogger(MowersFileHandler.class);

    /**
     * Process textual input as lawn and mowers configuration
     * @param filename name of file containing input
     * @return the serialised final positions of the mowers
     */
    @Override
    public String processMowers(String filename) {
        MowersFileHandler.logger.debug("Attempting to process instructions file {}", filename);
        try (final BufferedReader reader = Files.newBufferedReader(Path.of(filename))) {
            final LawnStreamInstructionsStreamer streamer = new LawnInputStreamParser(reader);
            final LawnStreamInstructionsStreamer.LawnInstructionsStream instructions = streamer.instructions();

            MowersFileHandler.logger.debug("Successfully read instructions from file");

            final Lawn lawn = new GridLawn(instructions.grid());

            return instructions.mowers()
                .map((LawnStreamInstructionsStreamer.MowerInstructions mowerInstructions) -> {
                    MowersFileHandler.logger.debug("Moving mower from position {} following instructions {}", mowerInstructions.initialPosition(), mowerInstructions.movements());

                    final MowerPosition finalPosition = lawn.moveMower(
                        new Mower() {},
                        mowerInstructions.movements(),
                        mowerInstructions.initialPosition()
                    );

                    MowersFileHandler.logger.debug("Successfully moved mower to position {}", finalPosition);

                    return this.serialisePosition(finalPosition);
                })
                .collect(Collectors.joining("\n"));
        } catch (Exception error) {
            MowersFileHandler.logger.error("Failed to process file", error);
            throw new RuntimeException("Failed to process file", error);
        }
    }
}
