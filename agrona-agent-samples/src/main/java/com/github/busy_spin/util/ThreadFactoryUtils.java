package com.github.busy_spin.util;

import java.util.concurrent.ThreadFactory;

public final class ThreadFactoryUtils {

    private ThreadFactoryUtils() {
    }

    public static ThreadFactory newThreadFactory(boolean isDaemon) {
        return r -> {
            Thread thread = new Thread(r);
            thread.setDaemon(isDaemon);
            return thread;
        };
    }

}
