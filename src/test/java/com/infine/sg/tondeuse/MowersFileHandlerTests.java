package com.infine.sg.tondeuse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.FileWriter;
import java.util.UUID;

public class MowersFileHandlerTests {
    /**
     * Generate a temporary file containing test instructions to be used by the test
     * @param contents the test instructions to be inserted into the file
     * @return the path and name of the generated file
     */
    private static String generateTestFile(String contents) {
        final String filePrefix = UUID.randomUUID().toString();
        final String fileSuffix = "txt";
        try {
            final File file = File.createTempFile(filePrefix, fileSuffix);

            final FileWriter fileWriter = new FileWriter(file);

            fileWriter.write(contents);
            fileWriter.close();

            return file.getAbsolutePath();
        } catch (Exception error) {
            MowersFileHandlerTests.logger.error("Unable to create test file", error);
            throw new RuntimeException(error);
        }
    }

    /**
     * The instructions to be given to the mower handler
     */
    private final static String mowerInstructions = "5 5\n1 2 N\nGAGAGAGAA\n3 3 E\nAADAADADDA";

    /**
     * The expected results from the mower handler
     */
    private final static String mowerResults = "1 3 N\n5 1 E";

    @Test
    void shouldCorrectlyProcessFileInstructions() {
        final String filename = MowersFileHandlerTests.generateTestFile(MowersFileHandlerTests.mowerInstructions);

        final MowersFileHandler mowersFileHandler = new MowersFileHandler();

        final String mowerResults = mowersFileHandler.processMowers(filename);

        Assertions.assertEquals(MowersFileHandlerTests.mowerResults, mowerResults, "Expected correct result from processing mowers");

    }

    private static final Logger logger = LoggerFactory.getLogger(MowersFileHandlerTests.class);
}
