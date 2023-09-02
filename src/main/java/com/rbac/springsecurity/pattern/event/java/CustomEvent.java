package com.rbac.springsecurity.pattern.event.java;

import java.util.EventObject;

public class CustomEvent extends EventObject {
    public CustomEvent(Object source) {
        super(source);
    }
}
