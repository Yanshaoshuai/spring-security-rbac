package com.rbac.springsecurity.pattern.Delegate;

public class FactoryWorker implements Worker{
    @Override
    public void work() {
        System.out.println("I can work in factory");
    }
}
