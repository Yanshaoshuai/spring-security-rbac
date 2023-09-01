package com.rbac.springsecurity.pattern.Delegate;

public class BuildingWorker implements Worker{
    @Override
    public void work() {
        System.out.println("I can work in building");
    }
}
