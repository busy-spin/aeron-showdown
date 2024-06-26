package com.github.busy_spin.backoff;

import com.github.busy_spin.util.ThreadFactoryUtils;
import org.agrona.concurrent.*;
import org.agrona.concurrent.status.AtomicCounter;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

public class NoWorkAgentSample {

    public static void main(String[] args) {
        ShutdownSignalBarrier barrier = new ShutdownSignalBarrier();
        SigInt.register(barrier::signal);

        UnsafeBuffer buffer = new UnsafeBuffer(ByteBuffer.allocateDirect(2048));
        AtomicCounter errorCounter = new AtomicCounter(buffer, 0);
        Agent agent = new CustomNameCompositeAgent("Composite-Agent", new LogInvocationAgent());
        try (AgentRunner runner = new AgentRunner(
                new ExponentialBackOffIdleStrategy(TimeUnit.SECONDS.toNanos(1),
                        TimeUnit.SECONDS.toNanos(8)),
                Throwable::printStackTrace,
                errorCounter,
                agent)) {

            AgentRunner.startOnThread(runner, ThreadFactoryUtils.newThreadFactory(true));
            barrier.await();
        } finally {
            System.out.println("Exiting the program !!!");
        }

    }
}
