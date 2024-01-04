package com.infine.sg.tondeuse;

/**
 * A type which may process a file of lawn configuration and mower instructions
 */
public interface MowersHandler {
    /**
     * Process textual input as lawn and mowers configuration
     * @param filename name of file containing input
     * @return the serialised final positions of the mowers
     */
    String processMowers(String filename);
}
