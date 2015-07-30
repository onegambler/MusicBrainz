package com.musicbrainz.rest;

import com.google.common.base.Throwables;
import com.musicbrainz.util.MusicBrainzLogger;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

public interface RESTClient {

    String get(Request request);

    class ConnectionBouncer {

        private static final int RATE = 1500;
        private static final boolean FAIR = true;

        private final Semaphore semaphore;

        private final MusicBrainzLogger LOGGER = MusicBrainzLogger.getLogger();
        private final Timer timer;

        private ConnectionBouncer(int rate, boolean fair) {
            semaphore = new Semaphore(0, fair);
            timer = new Timer();
            timer.scheduleAtFixedRate(new TokenGenerator(), 0, rate);
        }

        public void stop() {
            timer.cancel();
        }

        public static ConnectionBouncer getInstance() {
            return SingletonHolder.INSTANCE;
        }

        public void waitTurn() {
            try {
                LOGGER.logDebug("Thread: %s. Requesting token for call", String.valueOf(Thread.currentThread().getId()));
                semaphore.acquire();
                LOGGER.logDebug("Thread: %s. Token acquired, proceeding..", String.valueOf(Thread.currentThread().getId()));
            } catch (InterruptedException e) {
                Throwables.propagate(e);
            }
        }

        private static class SingletonHolder {
            private static final ConnectionBouncer INSTANCE = new ConnectionBouncer(RATE, FAIR);
        }

        private class TokenGenerator extends TimerTask {

            public void run() {
                if (semaphore.availablePermits() == 0) {
                    LOGGER.logDebug("Token generator: released a new token");
                    semaphore.release();
                } else {
                    LOGGER.logDebug("Token generator: a token is available, no need to release a new one");
                }
            }
        }
    }
}
