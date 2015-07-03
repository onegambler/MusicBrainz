package com.kobalt.util;

import com.google.common.base.Throwables;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

public class ConnectionBouncer {

    private static final int RATE = 1500;
    private static final boolean FAIR = true;

    private final Semaphore semaphore;

    private ConnectionBouncer(int rate, boolean fair) {
        semaphore = new Semaphore(0, fair);
        new Timer().scheduleAtFixedRate(new TokenGenerator(), 0, rate);
    }

    public static ConnectionBouncer getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void waitTurn() {
        try {
            semaphore.acquire();
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
                semaphore.release();
            }
        }
    }
}
