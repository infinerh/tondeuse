package com.infine.sg.tondeuse.io;

import com.infine.sg.tondeuse.domain.Grid;
import com.infine.sg.tondeuse.mapping.InstructionsGridConfigurationParser;
import com.infine.sg.tondeuse.mapping.InstructionsMowerConfigurationParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * An implementation of LawnStreamInstructionsStreamer which will stream the instructions read from its reader.
 * NB: may only be used once
 */
public class LawnInputStreamParser implements LawnStreamInstructionsStreamer, InstructionsGridConfigurationParser, InstructionsMowerConfigurationParser {
    private final static Logger logger = LogManager.getLogger(LawnInputStreamParser.class);
    private final BufferedReader bufferedReader;

    /**
     * Instantiate the parser with the input stream from which it will read
     * @param reader
     */
    public LawnInputStreamParser(final BufferedReader reader) {
        Objects.requireNonNull(reader);

        this.bufferedReader = reader;
    }

    /**
     * Stream instructions for a lawn and its mowers
     * @return
     */
    @Override
    public LawnInstructionsStream instructions() {
        try {
            final String gridConfiguration = this.bufferedReader.readLine();
            final Grid grid = this.gridFromString(gridConfiguration);

            LawnInputStreamParser.logger.debug("Read grid configuration {}", grid);

            return new LawnInstructionsStream(
                grid,
                LawnInputStreamParser.mowerLines(this.bufferedReader)
                    .map((MowerConfigurationText mowerConfiguration) -> new MowerInstructions(
                        this.positionFromString(mowerConfiguration.configuration),
                        mowerConfiguration.movements
                            .chars()
                            .mapToObj(this::movementFromCharacter)
                    ))
            );
        } catch (IOException error) {
            LawnInputStreamParser.logger.error("Unable to read instructions input", error);
            throw new RuntimeException("Unable to read instructions input", error);
        }
    }

    /**
     * A type containing the text parameters in which mower configuration and instructions are serialised
     * @param configuration
     * @param movements
     */
    private record MowerConfigurationText(String configuration, String movements) {

    }

    /**
     * Stream mower configurations from tje given reader by consuming the number of lines needed for one configuration each time
     * @param reader
     * @return
     */
    private static Stream<MowerConfigurationText> mowerLines(final BufferedReader reader) {
        Iterator<MowerConfigurationText> iter = new Iterator<>() {
            MowerConfigurationText nextLine = null;

            @Override
            public boolean hasNext() {
                if (nextLine != null) {
                    return true;
                } else {
                    try {
                        final String configuration = reader.readLine();
                        if (configuration != null) {
                            final String movements = reader.readLine();
                            if (movements == null) {
                                throw new IllegalArgumentException("Configuration contained mower configuration line without corresponding mower movements line");
                            } else {
                                nextLine = new MowerConfigurationText(configuration, movements);
                            }
                        }
                        return (nextLine != null);
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                }
            }

            @Override
            public MowerConfigurationText next() {
                if (nextLine != null || hasNext()) {
                    MowerConfigurationText line = nextLine;
                    nextLine = null;
                    return line;
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(
            iter, Spliterator.ORDERED | Spliterator.NONNULL), false);
    }
}
