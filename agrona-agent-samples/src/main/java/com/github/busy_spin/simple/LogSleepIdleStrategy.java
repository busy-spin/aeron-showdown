package com.github.busy_spin.simple;

import org.agrona.concurrent.IdleStrategy;

import java.sql.Time;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class LogSleepIdleStrategy implements IdleStrategy {
    @Override
    public void idle(int workCount) {
        if (workCount <= 0) {
            System.out.printf("[%s], Going for a sleep\n", Thread.currentThread().getName());
            LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(2L));
        } else {
            System.out.printf("[%s] - work count = %d\n", Thread.currentThread().getName(), workCount);
        }
    }

    @Override
    public void idle() {

    }

    @Override
    public void reset() {

    }

    @Override
    public String alias() {
        return IdleStrategy.super.alias();
    }
}
