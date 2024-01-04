package com.infine.sg.tondeuse;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Invalid filename");
        } else {
            try {
                final String filename = args[0];
                final MowersHandler handler = new MowersFileHandler();

                final String result = handler.processMowers(filename);

                System.out.println(result);
            } catch (Exception exception) {
                System.out.println(String.format("Failed to parse instructions: %s", exception.getLocalizedMessage()));
            }
        }
    }
}