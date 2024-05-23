package com.github.busy_spin;

import org.agrona.concurrent.IdleStrategy;

import java.util.concurrent.locks.LockSupport;

public class ExponentialBackOffIdleStrategy implements IdleStrategy {

    private final long maxBackOff;

    private final long initialBackoff;

    private long backoffCounter;

    private long currentBackOff = 0;

    public ExponentialBackOffIdleStrategy(long initialBackoff, long maxBackOff) {
        this.maxBackOff = maxBackOff;
        this.initialBackoff = initialBackoff;
    }


    @Override
    public void idle(int workCount) {
        if (workCount > 0) {
            backoffCounter = 0;
            currentBackOff = 0;
        } else {
            if (currentBackOff < maxBackOff) {
                long sleepTime = initialBackoff * Math.round(Math.pow(2, backoffCounter));
                backoffCounter++;
                currentBackOff = Math.min(sleepTime, maxBackOff);
                System.out.printf("A new backoff calculated %dms\n", Math.round(currentBackOff * 1e-6));
            }

            LockSupport.parkNanos(currentBackOff);
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
