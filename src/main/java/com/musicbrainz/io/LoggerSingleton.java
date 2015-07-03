package com.musicbrainz.io;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerSingleton {

    private static Logger logger;

    private LoggerSingleton() {

    }

    public static Logger getLogger() {
        if (logger == null) {
            try {
                logger = Logger.getLogger("logger");
                FileHandler fh = new FileHandler("log.log");
                logger.addHandler(fh);
                SimpleFormatter formatter = new SimpleFormatter();
                fh.setFormatter(formatter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return logger;
    }
}
