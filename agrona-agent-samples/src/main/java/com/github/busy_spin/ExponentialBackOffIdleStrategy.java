package com.github.busy_spin;

import org.agrona.concurrent.IdleStrategy;

import java.util.concurrent.locks.LockSupport;

public class ExponentialBackOffIdleStrategy implements IdleStrategy {

    private final long maxBackOff;

    private final long initialBackoff;

    private long backoffCounter;

    public ExponentialBackOffIdleStrategy(long initialBackoff, long maxBackOff) {
        this.maxBackOff = maxBackOff;
        this.initialBackoff = initialBackoff;
    }


    @Override
    public void idle(int workCount) {
        if (workCount > 0) {
            backoffCounter = 0;
        } else {
            long sleepTime = initialBackoff * Math.round(Math.pow(2, backoffCounter));
            backoffCounter++;
            long maxSleepTime = Math.min(sleepTime, maxBackOff);

            LockSupport.parkNanos(maxSleepTime);
        }
    }

    @Override
    public void idle() {
        LockSupport.parkNanos(this.initialBackoff);
    }

    @Override
    public void reset() {

    }

    @Override
    public String alias() {
        return IdleStrategy.super.alias();
    }
}
