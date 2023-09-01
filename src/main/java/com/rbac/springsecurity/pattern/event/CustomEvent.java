package com.rbac.springsecurity.pattern.event;

import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class CustomEvent extends ApplicationEvent {
    public CustomEvent(Object source) {
        super(source);
    }

    public CustomEvent(Object source, Clock clock) {
        super(source, clock);
    }
}
