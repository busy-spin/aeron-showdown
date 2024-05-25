package com.github.busy_spin.backoff;

import org.agrona.concurrent.Agent;
import org.agrona.concurrent.CompositeAgent;

public class CustomNameCompositeAgent extends CompositeAgent {
    private final String roleName;

    public CustomNameCompositeAgent(String roleName, Agent... agents) {
        super(agents);
        this.roleName = roleName;
    }

    @Override
    public String roleName() {
        return roleName;
    }
}
