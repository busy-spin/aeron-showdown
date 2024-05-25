package com.github.busy_spin.simple;

import org.agrona.concurrent.AgentRunner;
import org.agrona.concurrent.ShutdownSignalBarrier;
import org.agrona.concurrent.SigInt;

public class SimpleAgentSample {
    public static void main(String[] args) {
        ShutdownSignalBarrier barrier = new ShutdownSignalBarrier();
        try (AgentRunner agentRunner = new AgentRunner(new LogSleepIdleStrategy(),
                throwable -> {}, null, new RandomWorkCountAgent())) {
            AgentRunner.startOnThread(agentRunner, r -> {
                Thread thread = new Thread(r);
                thread.setDaemon(true);
                return thread;
            });
            SigInt.register(barrier::signal);

            barrier.await();
        } finally {
            System.out.println("Exiting the program !!!");
        }
    }
}
