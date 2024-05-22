package com.github.busy_spin;

import org.agrona.concurrent.Agent;
import org.agrona.concurrent.SystemEpochClock;

public class NoWorkAgent implements Agent {

    long lastInvocationTime = -1;

    @Override
    public void onStart() {
        Agent.super.onStart();
        System.out.println("Agent started");
    }

    @Override
    public int doWork() throws Exception {
        long timeNow = SystemEpochClock.INSTANCE.time();
        if (lastInvocationTime > 0) {
            long invocationGap = timeNow - lastInvocationTime;
            System.out.printf("Gap between invocations %d\n", invocationGap);
        }
        lastInvocationTime = timeNow;
        return 0;
    }

    @Override
    public void onClose() {
        System.out.println("Agent closed");
        Agent.super.onClose();
    }

    @Override
    public String roleName() {
        return "No-Work";
    }
}
