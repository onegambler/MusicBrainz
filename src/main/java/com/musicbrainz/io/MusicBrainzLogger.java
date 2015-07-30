package com.musicbrainz.io;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MusicBrainzLogger {

    private static Logger logger;
    private static final String LOGGER_PATH = "musicbrainz.log";

    private MusicBrainzLogger() {
        logger =  Logger.getLogger("logger");
        try {
            FileHandler fh = new FileHandler(LOGGER_PATH);
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        } catch (Exception e) {
            logger.severe("Impossible to set a file handler for the logger");
        }
    }

    public static MusicBrainzLogger getLogger() {
        return SingletonHolder.LOGGER;
    }

    public static class SingletonHolder {
        public static final MusicBrainzLogger LOGGER = new MusicBrainzLogger();
    }

    public void logInfo(String message, String... args) {
        if(logger.isLoggable(Level.INFO)) {
            logger.info(String.format(message, args));
        }
    }

    public void logWarning(String message, String... args) {
        if(logger.isLoggable(Level.WARNING)) {
            logger.warning(String.format(message, args));
        }
    }

    public void logError(String message, String... args) {
        logger.severe(String.format(message, args));
    }

    public void logError(Throwable e, String message, String... args) {
        logger.log(Level.SEVERE, String.format(message, args), e);
    }
}
