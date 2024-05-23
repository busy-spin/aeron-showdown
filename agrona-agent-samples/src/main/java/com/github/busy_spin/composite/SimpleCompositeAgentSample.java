package com.github.busy_spin.composite;

import com.github.busy_spin.util.ThreadFactoryUtils;
import org.agrona.concurrent.AgentRunner;
import org.agrona.concurrent.CompositeAgent;
import org.agrona.concurrent.ShutdownSignalBarrier;
import org.agrona.concurrent.SleepingIdleStrategy;

import java.util.concurrent.TimeUnit;

public class SimpleCompositeAgentSample {

    public static void main(String[] args) {
        int agentCount = 5;
        PrintNameAgent[] agents = new PrintNameAgent[agentCount];
        for (int i = 0; i < agentCount; i++) {
            agents[i] = new PrintNameAgent("Agent" + i);
        }
        ShutdownSignalBarrier barrier = new ShutdownSignalBarrier();
        try (AgentRunner agentRunner = new AgentRunner(new SleepingIdleStrategy(TimeUnit.SECONDS.toNanos(2)),
                Throwable::printStackTrace, null,
                new CompositeAgent(agents))) {
            AgentRunner.startOnThread(agentRunner, ThreadFactoryUtils.newThreadFactory(true));
            barrier.await();
        } finally {
            System.out.println("Exiting the program");
        }
    }
}
