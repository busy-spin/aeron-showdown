package com.github.busy_spin;

import org.agrona.concurrent.*;
import org.agrona.concurrent.status.AtomicCounter;

import java.nio.ByteBuffer;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class NoWorkAgentSample {

    public static void main(String[] args) {
        ShutdownSignalBarrier barrier = new ShutdownSignalBarrier();
        SigInt.register(barrier::signal);

        UnsafeBuffer buffer = new UnsafeBuffer(ByteBuffer.allocateDirect(2048));
        AtomicCounter errorCounter = new AtomicCounter(buffer, 0);
        try (AgentRunner runner = new AgentRunner(
                new ExponentialBackOffIdleStrategy(TimeUnit.SECONDS.toNanos(1),
                        TimeUnit.SECONDS.toNanos(8)),
                Throwable::printStackTrace,
                errorCounter,
                new LogInvocationAgent())) {

            AgentRunner.startOnThread(runner, newThreadFactory(true));
            barrier.await();
        } finally {
            System.out.println("Exiting the program !!!");
        }

    }

    private static ThreadFactory newThreadFactory(boolean isDaemon) {
        return r -> {
            Thread thread = new Thread(r);
            thread.setDaemon(isDaemon);
            return thread;
        };
    }
}
