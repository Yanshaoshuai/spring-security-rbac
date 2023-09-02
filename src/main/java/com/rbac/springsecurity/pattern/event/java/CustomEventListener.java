package com.rbac.springsecurity.pattern.event.java;

import java.util.EventListener;

public class CustomEventListener implements EventListener {
    public void listen(CustomEvent event){
        System.out.println(event.getSource());
    }
}
