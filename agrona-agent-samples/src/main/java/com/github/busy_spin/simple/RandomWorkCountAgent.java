package com.github.busy_spin.simple;

import org.agrona.concurrent.Agent;

import java.util.Random;
import java.util.stream.IntStream;

public class RandomWorkCountAgent implements Agent {

    private final Random random = new Random();

    @Override
    public void onStart() {
        System.out.printf("[%s], Agent started\n", Thread.currentThread().getName());
    }

    @Override
    public int doWork() throws Exception {
        return random.nextInt(0, 3);
    }

    @Override
    public void onClose() {
        Agent.super.onClose();
    }

    @Override
    public String roleName() {
        return "random-agent";
    }
}
