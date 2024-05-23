package com.github.busy_spin.composite;

import org.agrona.concurrent.Agent;

public class PrintNameAgent implements Agent {

    private final String name;

    public PrintNameAgent(String name) {
        this.name = name;
    }

    @Override
    public void onStart() {
        Agent.super.onStart();
    }

    @Override
    public int doWork() throws Exception {
        System.out.printf("Agent  %s on duty on Thread %s\n", name, Thread.currentThread().getName());
        return 0;
    }

    @Override
    public void onClose() {
        System.out.println("Closing agent");
        Agent.super.onClose();
    }

    @Override
    public String roleName() {
        return name;
    }
}
