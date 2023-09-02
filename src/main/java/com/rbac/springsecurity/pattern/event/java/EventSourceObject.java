package com.rbac.springsecurity.pattern.event.java;

import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;

public class EventSourceObject {
    private String name;
    // 监听器容器
    private Set<CustomEventListener> listeners;
    public EventSourceObject(String name) {
        this.listeners = new HashSet<CustomEventListener>();
        this.name = StringUtils.isEmpty(name) ? "defaultname" : name;
    }

    // 给事件源注册监听器
    public void addCusListener(CustomEventListener cel) {
        this.listeners.add(cel);
    }

    // 当事件发生时,通知注册在该事件源上的所有监听器做出相应的反应（调用回调方法）
    public void emitEvent(CustomEvent event) {
        for (CustomEventListener listener : listeners) {
            listener.listen(event);
        }
    }
    public String getName() {
        return name;
    }

    public static void main(String[] args) {
        CustomEvent customEvent = new CustomEvent("custom event");
        CustomEventListener customEventListener = new CustomEventListener();
        EventSourceObject eventSource = new EventSourceObject("event source");
        eventSource.addCusListener(customEventListener);
        eventSource.emitEvent(customEvent);
    }
}
